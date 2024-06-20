package io.github.hebertfsoares.ms_animals.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_animals.domain.entities.Client;
import io.github.hebertfsoares.ms_animals.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientConsumer {

    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;

    @Value("${mq.queue.client_registration}")
    private String queueName;

    @RabbitListener(queues = "${mq.queue.client_registration}")
    public void receiveClientInfo(String payload) {
        try {
            DataClientMQ data = objectMapper.readValue(payload, DataClientMQ.class);
            Client client = new Client(data.getId(), data.getName(), data.getCpf());
            clientRepository.save(client);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}