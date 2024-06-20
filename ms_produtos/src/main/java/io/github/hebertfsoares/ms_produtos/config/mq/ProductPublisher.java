package io.github.hebertfsoares.ms_produtos.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_produtos.dto.ProductForSales;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.queue.product_registration}")
    private String queueName;

    public void sendProductInfo(ProductForSales data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(queueName, json);
    }
}
