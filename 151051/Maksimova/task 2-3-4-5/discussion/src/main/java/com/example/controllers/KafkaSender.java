package com.example.controllers;

import com.example.dto.PostResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, PostResponseTo> userKafkaTemplate;


    void sendCustomMessage(PostResponseTo postResponseTo, String topicName) {
        userKafkaTemplate.send(topicName, postResponseTo);
    }
}
