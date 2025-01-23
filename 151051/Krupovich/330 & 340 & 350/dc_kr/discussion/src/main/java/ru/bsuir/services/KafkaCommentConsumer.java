package ru.bsuir.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bsuir.entity.CComment;

@Service
public class KafkaCommentConsumer {

    @KafkaListener(topics = "comments-topic", groupId = "comment-group")
    public void consumeComment(CComment comment) {
        System.out.println("Request comment: " + comment);
    }
}
