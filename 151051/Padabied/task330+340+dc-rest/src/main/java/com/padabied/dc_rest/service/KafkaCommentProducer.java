package com.padabied.dc_rest.service;

import com.padabied.dc_rest.model.Comment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaCommentProducer {
    private final KafkaTemplate<String, Comment> kafkaTemplate;

    public KafkaCommentProducer(KafkaTemplate<String, Comment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendComment(Comment comment) {
        kafkaTemplate.send("comments-topic", comment);
    }
}
