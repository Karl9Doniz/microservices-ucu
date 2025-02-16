package com.example.facadeservice.dto;

import java.util.UUID;

public class LogMessageRequest {
    private UUID id;
    private String message;

    public LogMessageRequest() {
    }

    public LogMessageRequest(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
