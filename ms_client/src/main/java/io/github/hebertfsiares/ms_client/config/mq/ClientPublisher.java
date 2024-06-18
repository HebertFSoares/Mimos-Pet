package io.github.hebertfsiares.ms_client.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsiares.ms_client.dto.ClientForAnimals;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.queue.client_registration}")
    private String queueName;

    public void sendClientInfo(ClientForAnimals data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(queueName, json);
    }
}