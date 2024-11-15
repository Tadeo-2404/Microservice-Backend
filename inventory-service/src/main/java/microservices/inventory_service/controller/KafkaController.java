package microservices.inventory_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.inventory_service.service.KafkaProducerService;

@RestController
public class KafkaController {

    private final KafkaProducerService producerService;

    @Autowired
    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        producerService.sendMessage("my-topic", message);
        return ResponseEntity.ok("Message published to Kafka topic");
    }
}