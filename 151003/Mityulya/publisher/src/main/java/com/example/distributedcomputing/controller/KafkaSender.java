package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.MessageRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private  final KafkaTemplate<String, MessageRequestTo> userKafkaTemplate;


    void sendCustomMessage(MessageRequestTo commentRequestTo, String topicName) {
        userKafkaTemplate.send(topicName, commentRequestTo);
    }
}