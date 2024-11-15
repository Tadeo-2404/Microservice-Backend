package microservices.product_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            // Send the message asynchronously
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            // Handle any errors in sending the message
            log.error("Failed to send Kafka message to topic {}: {}", topic, e.getMessage());
            throw new KafkaException("Failed to send Kafka message", e);
        }
    }    
}