package com.example.messagesservice.controller;

import com.example.messagesservice.service.KafkaConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private final KafkaConsumerService kafkaConsumerService;

    public MessagesController(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getMessages() {
        return ResponseEntity.ok(kafkaConsumerService.getMessages());
    }
}
