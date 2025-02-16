package com.example.messagesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @GetMapping
    public ResponseEntity<String> getStaticMessage() {
        return ResponseEntity.ok("Not implemented yet");
    }
}