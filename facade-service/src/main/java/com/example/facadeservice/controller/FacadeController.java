package com.example.facadeservice.controller;

import com.example.facadeservice.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/facade")
public class FacadeController {
    private final KafkaProducerService kafkaProducerService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> messagesServiceUrls = List.of(
            "http://localhost:8084/api/messages",
            "http://localhost:8085/api/messages"
    );
    private final Random random = new Random();

    public FacadeController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String msg) {
        kafkaProducerService.sendMessage(msg);
        return ResponseEntity.status(201).body("Message sent to Kafka: " + msg);
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        String url = messagesServiceUrls.get(random.nextInt(messagesServiceUrls.size()));
        try {
            String messages = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok("Messages: " + messages);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve messages from " + url);
        }
    }
}
