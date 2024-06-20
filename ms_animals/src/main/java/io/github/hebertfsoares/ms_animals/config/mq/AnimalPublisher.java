package io.github.hebertfsoares.ms_animals.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_animals.dto.AnimalForAdoption;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.queue.animal_registration}")
    private String queueName;

    public void sendAnimalInfo(AnimalForAdoption data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(queueName, json);
    }
}