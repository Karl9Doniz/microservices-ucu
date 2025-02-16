package com.example.facadeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@RestController
@RequestMapping("/api/facade")
public class FacadeController {

    private final RestClient restClient;

    private static final String LOGGING_SERVICE_URL = "http://localhost:8081/api/logging";
    private static final String MESSAGES_SERVICE_URL = "http://localhost:8082/api/messages";

    public FacadeController() {
        this.restClient = RestClient.create();
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody String msg) {
        UUID uuid = UUID.randomUUID();
        LogMessageRequest payload = new LogMessageRequest(uuid, msg);

        restClient.post()
                .uri(LOGGING_SERVICE_URL)
                .body(payload)
                .retrieve()
                .toBodilessEntity();

        return ResponseEntity.status(201).body("Message stored: " + msg);
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages() {
        String logs = restClient.get()
                .uri(LOGGING_SERVICE_URL)
                .retrieve()
                .body(String.class);

        String staticMsg = restClient.get()
                .uri(MESSAGES_SERVICE_URL)
                .retrieve()
                .body(String.class);

        String combinedResponse = "Logs: " + logs + "\nStatic Message: " + staticMsg;
        return ResponseEntity.ok(combinedResponse);
    }
}
