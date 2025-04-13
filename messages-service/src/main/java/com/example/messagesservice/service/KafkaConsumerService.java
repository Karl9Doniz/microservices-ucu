package com.example.messagesservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {

    private final List<String> messages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "message-topic", groupId = "messages-group")
    public void listen(String message) {
        messages.add(message);
        System.out.println("Received message: " + message);
    }

    public List<String> getMessages() {
        return messages;
    }
}

