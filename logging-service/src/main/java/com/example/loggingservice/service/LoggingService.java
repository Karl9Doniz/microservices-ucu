package com.example.loggingservice.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoggingService {
    private final Map<String, String> messageStore = new HashMap<>();

    public void storeMessage(String id, String message) {
        messageStore.put(id, message);
        System.out.println("Stored message: " + message);
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(messageStore.values());
    }
}
