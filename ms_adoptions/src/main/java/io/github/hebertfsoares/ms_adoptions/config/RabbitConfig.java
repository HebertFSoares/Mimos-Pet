package io.github.hebertfsoares.ms_adoptions.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${mq.queue.client_registration}")
    private String clientRegistrationQueue;

    @Bean
    public Queue clientRegistrationQueue() {
        logger.info("Client Registration Queue: {}", clientRegistrationQueue);
        return new Queue(clientRegistrationQueue, true);
    }

    @Value("${mq.queue.animal_registration}")
    private String animalRegistrationQueue;

    @Bean
    public Queue animalRegistrationQueue() {
        logger.info("Animal Registration Queue: {}", animalRegistrationQueue);
        return new Queue(animalRegistrationQueue, true);
    }
}
