package com.example.discussion.service;

import com.example.discussion.model.Comment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaCommentConsumer {

    @KafkaListener(topics = "comments-topic", groupId = "comment-group")
    public void consumeComment(Comment comment) {
        System.out.println("Получен комментарий: " + comment);
    }
}
