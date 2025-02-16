package com.example.facadeservice.controller;

import com.example.facadeservice.dto.LogMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/api/facade")
public class FacadeController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String LOGGING_SERVICE_URL = "http://localhost:8081/api/logging";
    private static final String MESSAGES_SERVICE_URL = "http://localhost:8082/api/messages";

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody String msg) {
        String uuid = UUID.randomUUID().toString();
        LogMessageRequest payload = new LogMessageRequest(uuid, msg);

        restTemplate.postForEntity(LOGGING_SERVICE_URL, payload, String.class);

        return ResponseEntity.ok("Message received and forwarded: " + msg);
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        ResponseEntity<String> logsResponse = restTemplate.getForEntity(LOGGING_SERVICE_URL, String.class);
        ResponseEntity<String> msgResponse = restTemplate.getForEntity(MESSAGES_SERVICE_URL, String.class);
        String combinedResponse = "Logs: " + logsResponse.getBody() + "\nStatic Message: " + msgResponse.getBody();
        return ResponseEntity.ok(combinedResponse);
    }
}
