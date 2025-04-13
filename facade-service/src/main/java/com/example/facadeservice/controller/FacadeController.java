package com.example.facadeservice.controller;

import com.example.facadeservice.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/facade")
public class FacadeController {
    private final KafkaProducerService kafkaProducerService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> messagesServiceUrls = List.of(
            "http://localhost:8084/api/messages",
            "http://localhost:8085/api/messages"
    );
    private final List<String> loggingServiceUrls = List.of(
            "http://localhost:8081/api/logging",
            "http://localhost:8082/api/logging",
            "http://localhost:8083/api/logging"
    );
    private final Random random = new Random();

    public FacadeController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String msg) {
        kafkaProducerService.sendMessage(msg);

        String loggingUrl = loggingServiceUrls.get(random.nextInt(loggingServiceUrls.size()));
        LogMessageRequest request = new LogMessageRequest(UUID.randomUUID(), msg);
        restTemplate.postForObject(loggingUrl, request, String.class);

        return ResponseEntity.status(201).body("Message processed: " + msg);
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        String messagesUrl = messagesServiceUrls.get(random.nextInt(messagesServiceUrls.size()));
        String messages = restTemplate.getForObject(messagesUrl, String.class);

        String loggingUrl = loggingServiceUrls.get(random.nextInt(loggingServiceUrls.size()));
        String logs = restTemplate.getForObject(loggingUrl, String.class);

        return ResponseEntity.ok("Messages: " + messages + "\nLogs: " + logs);
    }
}
