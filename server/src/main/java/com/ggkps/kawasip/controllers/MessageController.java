package com.ggkps.kawasip.controllers;

import com.ggkps.kawasip.config.MessageBroker.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message ) {
        messageSender.sendMessage(message);
        return ResponseEntity.ok(message);
    }
}
