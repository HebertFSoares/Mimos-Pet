package io.github.hebertfsoares.ms_client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CLIENT_REGISTRATION_QUEUE = "client_registration_queue";
    public static final String CLIENT_REGISTRATION_EXCHANGE = "new_client_registration_fanout_exchange";

    @Bean
    Queue clientRegistrationQueue() {
        return new Queue(CLIENT_REGISTRATION_QUEUE, true);
    }

    @Bean
    public FanoutExchange clientRegistrationExchange() {
        return new FanoutExchange(CLIENT_REGISTRATION_EXCHANGE);
    }

    @Bean
    public Binding bindingClientRegistrationQueue() {
        return BindingBuilder.bind(clientRegistrationQueue()).to(clientRegistrationExchange());
    }
}
