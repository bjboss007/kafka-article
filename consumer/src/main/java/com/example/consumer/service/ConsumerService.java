package com.example.consumer.service;


import com.example.consumer.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Message> messages = new ArrayList<>();

    @KafkaListener(topics = "chat", groupId = "consumer-1", containerFactory = "chatFactory")
    private void chatConsumer(Message message){
        messages.add(message);
        logger.info("Received : {}", messages);
    }

}
