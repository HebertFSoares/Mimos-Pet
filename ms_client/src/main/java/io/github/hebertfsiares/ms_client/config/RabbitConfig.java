package io.github.hebertfsiares.ms_client.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${mq.queue.client_registration}")
    private String clientRegistrationQueue;

    @Bean
    public Queue clientRegistrationQueue() {
        return new Queue(clientRegistrationQueue, true);
    }
}
