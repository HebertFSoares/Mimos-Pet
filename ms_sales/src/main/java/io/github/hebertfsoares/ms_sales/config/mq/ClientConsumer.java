package io.github.hebertfsoares.ms_sales.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_sales.domain.entities.Client;
import io.github.hebertfsoares.ms_sales.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_sales.dto.ClientForSales;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ClientConsumer.class);
    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;

    @RabbitListener(queues = "${mq.queue.client_registration}")
    public void receiveClientInfo(String payload) {
        try {
            logger.info("Received message: {}", payload);
            ClientForSales data = objectMapper.readValue(payload, ClientForSales.class);
            Client client = new Client(data.getId(), data.getName());
            clientRepository.save(client);
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }
}
