package com.example.loggingservice.controller;

import com.example.facadeservice.dto.LogMessageRequest;
import com.example.loggingservice.service.LoggingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logging")
public class LoggingController {

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @PostMapping
    public ResponseEntity<String> logMessage(@RequestBody LogMessageRequest request) {
        loggingService.storeMessage(request.getId(), request.getMessage());
        return ResponseEntity.ok("Message logged successfully");
    }

    @GetMapping
    public ResponseEntity<List<String>> getMessages() {
        return ResponseEntity.ok(loggingService.getAllMessages());
    }
}