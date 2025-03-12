package com.example.facadeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/facade")
public class FacadeController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> loggingServiceUrls = List.of(
            "http://localhost:8081/api/logging",
            "http://localhost:8082/api/logging",
            "http://localhost:8083/api/logging"
    );
    private final Random random = new Random();

    private String getRandomLoggingServiceUrl() {
        return loggingServiceUrls.get(random.nextInt(loggingServiceUrls.size()));
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String msg) {
        UUID uuid = UUID.randomUUID();
        LogMessageRequest payload = new LogMessageRequest(uuid, msg);

        for (int i = 0; i < loggingServiceUrls.size(); i++) {
            String url = getRandomLoggingServiceUrl();
            try {
                restTemplate.postForEntity(url, payload, String.class);
                return ResponseEntity.status(201).body("Message stored: " + msg);
            } catch (Exception e) {
                System.out.println("Failed to connect to " + url + ". Trying next...");
            }
        }
        return ResponseEntity.status(500).body("All logging services are down.");
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        for (int i = 0; i < loggingServiceUrls.size(); i++) {
            String url = getRandomLoggingServiceUrl();
            try {
                String logs = restTemplate.getForObject(url, String.class);
                return ResponseEntity.ok("Logs: " + logs);
            } catch (Exception e) {
                System.out.println("Failed to connect to " + url + ". Trying next...");
            }
        }
        return ResponseEntity.status(500).body("All logging services are down.");
    }
}
