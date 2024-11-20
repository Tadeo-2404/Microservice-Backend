package microservices.inventory_service.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductEventListener {
    @KafkaListener(topics = "product-created")
    public void handleProductCreated(String message) {
        System.out.println("message kafka: " + message);
    }
}
