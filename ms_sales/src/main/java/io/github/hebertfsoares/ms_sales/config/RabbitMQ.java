package io.github.hebertfsoares.ms_sales.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQ.class);

    @Value("${mq.queue.client_registration}")
    private String clientRegistrationQueue;

    @Bean
    public Queue clientRegistrationQueue() {
        logger.info("Client Registration Queue: {}", clientRegistrationQueue);
        return new Queue(clientRegistrationQueue, true);
    }

    @Value("${mq.queue.product_registration}")
    private String productRegistrationQueue;

    @Bean
    public Queue productRegistrationQueue(){
        logger.info("Product Registration Queue: {}", productRegistrationQueue);
        return new Queue(productRegistrationQueue, true);
    }
}
