package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.CommentRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private  final KafkaTemplate<String, CommentRequestTo> userKafkaTemplate;


    void sendCustomMessage(CommentRequestTo commentRequestTo, String topicName) {
        userKafkaTemplate.send(topicName, commentRequestTo);
    }
}