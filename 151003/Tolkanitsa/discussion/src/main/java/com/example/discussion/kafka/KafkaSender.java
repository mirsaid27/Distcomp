package com.example.discussion.kafka;

import com.example.discussion.model.response.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, CommentResponseTo> userKafkaTemplate;


    public void sendCustomMessage(CommentResponseTo commentResponseTo, String topicName) {
        userKafkaTemplate.send(topicName, commentResponseTo);
    }
}