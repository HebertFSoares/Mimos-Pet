package io.github.hebertfsoares.ms_animals.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${mq.queue.animal_ms_registration}")
    private String animalRegistrationQueue;

    @Value("${mq.exchange.client_registration}")
    private String clientRegistrationExchange;

    @Bean
    public Queue animalRegistrationQueue() {
        logger.info("Animal Registration Queue: {}", animalRegistrationQueue);
        return new Queue(animalRegistrationQueue, true);
    }

    @Bean
    FanoutExchange clientRegistrationExchange() {
        return new FanoutExchange(clientRegistrationExchange);
    }

    @Bean
    Binding bindingAnimalRegistration(Queue animalRegistrationQueue, FanoutExchange clientRegistrationExchange) {
        return BindingBuilder.bind(animalRegistrationQueue).to(clientRegistrationExchange);
    }
}
