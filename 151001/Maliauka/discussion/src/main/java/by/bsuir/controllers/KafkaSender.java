package by.bsuir.controllers;

import by.bsuir.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, CommentResponseTo> userKafkaTemplate;


    void sendCustomMessage(CommentResponseTo commentResponseTo, String topicName) {
        userKafkaTemplate.send(topicName, commentResponseTo);
    }
}
