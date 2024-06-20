package io.github.hebertfsoares.ms_produtos.config.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queue.client_registration}")
    private String clientRegistrationQueue;

    @Value("${mq.queue.product_registration}")
    private String productRegistrationQueue;

    @Bean
    public Queue clientRegistrationQueue() {
        return new Queue(clientRegistrationQueue, true);
    }

    @Bean
    public Queue productRegistrationQueue() {
        return new Queue(productRegistrationQueue, true);
    }
}
