package by.bsuir.controllers;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, CommentRequestTo> userKafkaTemplate;


    void sendCustomMessage(CommentRequestTo commentRequestTo, String topicName) {
        userKafkaTemplate.send(topicName, commentRequestTo);
    }
}
