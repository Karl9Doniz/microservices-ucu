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
    private final KafkaProducerService producer;
    private final RestTemplate rest;

    public FacadeController(KafkaProducerService producer, RestTemplate rest) {
        this.producer = producer;
        this.rest = rest;
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String msg) {
        producer.sendMessage(msg);

        rest.postForObject(
                "http://logging-service/api/logging",
                new LogMessageRequest(UUID.randomUUID(), msg),
                String.class
        );

        return ResponseEntity.status(201).body("Message processed: " + msg);
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        String messages = rest.getForObject("http://messages-service/api/messages", String.class);
        String logs     = rest.getForObject("http://logging-service/api/logging", String.class);
        return ResponseEntity.ok("Messages: " + messages + "\nLogs: " + logs);
    }
}

