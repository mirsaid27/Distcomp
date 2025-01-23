package ru.bsuir.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.bsuir.entity.Post;

@Service
public class KafkaCommentProducer {
    private final KafkaTemplate<String, Post> kafkaTemplate;

    public KafkaCommentProducer(KafkaTemplate<String, Post> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendComment(Post post) {
        kafkaTemplate.send("comments-topic", post);
    }
}