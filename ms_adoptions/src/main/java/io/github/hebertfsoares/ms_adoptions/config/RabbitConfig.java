package io.github.hebertfsoares.ms_adoptions.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

    @Value("${mq.queue.animal_registration}")
    private String animalRegistrationQueue;

    @Value("${mq.exchange.client_registration}")
    private String clientRegistrationExchange;

    @Bean
    public Queue clientRegistrationQueue() {
        logger.info("Client Registration Queue: {}", clientRegistrationQueue);
        return new Queue(clientRegistrationQueue, true);
    }

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
    Binding bindingClientRegistration(Queue clientRegistrationQueue, FanoutExchange clientRegistrationExchange) {
        return BindingBuilder.bind(clientRegistrationQueue).to(clientRegistrationExchange);
    }

    @Bean
    Binding bindingAnimalRegistration(Queue animalRegistrationQueue, FanoutExchange clientRegistrationExchange) {
        return BindingBuilder.bind(animalRegistrationQueue).to(clientRegistrationExchange);
    }
}
