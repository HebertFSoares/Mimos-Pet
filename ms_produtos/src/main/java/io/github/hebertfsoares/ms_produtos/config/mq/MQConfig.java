package io.github.hebertfsoares.ms_produtos.config.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queue.client_registration}")
    private String nameQueue;

    @Bean
    public Queue queue(){
     return new Queue(nameQueue,true);
    }
}
