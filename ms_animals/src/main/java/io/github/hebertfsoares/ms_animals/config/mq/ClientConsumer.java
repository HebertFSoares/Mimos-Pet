package io.github.hebertfsoares.ms_animals.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_animals.domain.entities.Client;
import io.github.hebertfsoares.ms_animals.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_animals.dto.ClientForAnimals;
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

    @RabbitListener(queues = "${mq.queue.animal_ms_registration}")
    public void receiveClientInfo(String payload) {
        try {
            logger.info("Received message in msanimal: {}", payload);
            ClientForAnimals data = objectMapper.readValue(payload, ClientForAnimals.class);
            Client client = new Client(data.getId(), data.getName());
            clientRepository.save(client);
            logger.info("Client saved: {}", client);
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }
}
