package com.example.loggingservice.service;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoggingService {
    private final IMap<UUID, String> messageStore;

    public LoggingService() {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        this.messageStore = hazelcastInstance.getMap("messages");
    }

    public void storeMessage(UUID id, String message) {
        messageStore.put(id, message);
        System.out.println("Stored message with UUID " + id + ": " + message);
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(messageStore.values());
    }
}
