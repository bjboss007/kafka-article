package com.example.producer.api;


import com.example.producer.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping("send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message){
        kafkaTemplate.send("chat", UUID.randomUUID().toString(),  message);
        return ResponseEntity.ok("Message sent");
    }
}
