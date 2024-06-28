package io.github.hebertfsoares.ms_sales.config;

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
public class RabbitMQ {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQ.class);

    @Value("${mq.queue.sales_ms_registration}")
    private String clientRegistrationQueue;

    @Value("${mq.queue.product_registration}")
    private String productRegistrationQueue;

    @Value("${mq.exchange.client_registration}")
    private String clientRegistrationExchange;

    @Bean
    public Queue clientRegistrationQueue() {
        logger.info("Client Registration Queue: {}", clientRegistrationQueue);
        return new Queue(clientRegistrationQueue, true);
    }

    @Bean
    public Queue productRegistrationQueue() {
        logger.info("Product Registration Queue: {}", productRegistrationQueue);
        return new Queue(productRegistrationQueue, true);
    }

    @Bean
    FanoutExchange clientRegistrationExchange() {
        return new FanoutExchange(clientRegistrationExchange);
    }

    @Bean
    public Binding bindingClientRegistration(Queue clientRegistrationQueue, FanoutExchange clientRegistrationExchange) {
        return BindingBuilder.bind(clientRegistrationQueue).to(clientRegistrationExchange);
    }

}
