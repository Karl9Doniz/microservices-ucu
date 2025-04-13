package com.example.loggingservice.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoggingService {
    private final IMap<UUID, String> messageStore;
    private final HazelcastInstance hazelcastInstance;

    public LoggingService(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.messageStore = hazelcastInstance.getMap("messages");
        System.out.println("Hazelcast cluster members: " +
                hazelcastInstance.getCluster().getMembers());
    }

    public void storeMessage(UUID id, String message) {
        messageStore.put(id, message);
        System.out.printf(
                "Stored message '%s' with UUID %s on Hazelcast node: %s (Cluster size: %d)%n",
                message, id,
                hazelcastInstance.getLocalEndpoint().getUuid(),
                hazelcastInstance.getCluster().getMembers().size()
        );
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(messageStore.values());
    }
}