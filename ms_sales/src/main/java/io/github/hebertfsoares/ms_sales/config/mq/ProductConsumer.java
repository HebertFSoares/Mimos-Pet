package io.github.hebertfsoares.ms_sales.config.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hebertfsoares.ms_sales.domain.entities.Product;
import io.github.hebertfsoares.ms_sales.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_sales.dto.ProductForSales;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @RabbitListener(queues = "${mq.queue.product_registration}")
    public void receiveProductInfo(String payload){
        try {
            logger.info("Received message: {}", payload);
            ProductForSales data = objectMapper.readValue(payload, ProductForSales.class);
            Product product = new Product(data.getId(), data.getName());
            productRepository.save(product);
        }catch (JsonProcessingException e){
            logger.error("Error processing message",e);
        }
    }
}
