package io.github.hebertfsoares.ms_adoptions.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_adoptions.domain.entities.Animal;
import io.github.hebertfsoares.ms_adoptions.domain.repository.AnimalRepository;
import io.github.hebertfsoares.ms_adoptions.dto.AnimalForAdoption;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AnimalConsumer.class);
    private final ObjectMapper objectMapper;
    private final AnimalRepository animalRepository;

    @RabbitListener(queues = "${mq.queue.animal_registration}")
    public void receiveAnimalInfo(String payload) {
        try {
            logger.info("Received message: {}", payload);
            AnimalForAdoption data = objectMapper.readValue(payload, AnimalForAdoption.class);
            Animal animal = new Animal(data.getId(), data.getName());
            animalRepository.save(animal);
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }
}
