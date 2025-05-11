#!/bin/bash

# This script sets up the Consul configuration for the services

# Create Kafka configuration for messages-service
echo "Setting up Kafka configuration in Consul for messages-service..."
curl -X PUT -d '{
  "kafka": {
    "consumer": {
      "topic": "message-topic",
      "group-id": "messages-group"
    },
    "bootstrap-servers": "localhost:19092,localhost:19093,localhost:19094"
  }
}' http://localhost:8500/v1/kv/config/messages-service/data

# Create Kafka configuration for facade-service
echo "Setting up Kafka configuration in Consul for facade-service..."
curl -X PUT -d '{
  "kafka": {
    "producer": {
      "topic": "message-topic"
    },
    "bootstrap-servers": "localhost:19092,localhost:19093,localhost:19094"
  }
}' http://localhost:8500/v1/kv/config/facade-service/data

# Create Hazelcast configuration for logging-service
echo "Setting up Hazelcast configuration in Consul for logging-service..."
curl -X PUT -d '{
  "hazelcast": {
    "cluster-name": "logging-cluster",
    "network": {
      "join": {
        "multicast": {
          "enabled": true
        }
      }
    },
    "map": {
      "logs": {
        "backup-count": 1,
        "time-to-live-seconds": 86400
      }
    }
  }
}' http://localhost:8500/v1/kv/config/logging-service/data

echo "Consul configuration setup completed."