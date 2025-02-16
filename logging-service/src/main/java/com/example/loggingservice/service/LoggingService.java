package com.example.loggingservice.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoggingService {
    private final Map<UUID, String> messageStore = new HashMap<>();

    public void storeMessage(UUID id, String message) {
        messageStore.put(id, message);
        System.out.println("Stored message with UUID " + id + ": " + message);
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(messageStore.values());
    }
}
