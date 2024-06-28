package io.github.hebertfsiares.ms_client.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsiares.ms_client.dto.ClientForAnimals;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientPublisher {
    private static final Logger logger = LoggerFactory.getLogger(ClientPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange.client_registration}")
    private String exchangeName;

    public void sendClientInfo(ClientForAnimals data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        logger.info("Sending message to exchange: {}, message: {}", exchangeName, json);
        rabbitTemplate.convertAndSend(exchangeName, "", json);
        logger.info("Message sent to exchange: {}, message: {}", exchangeName, json);
    }
}
