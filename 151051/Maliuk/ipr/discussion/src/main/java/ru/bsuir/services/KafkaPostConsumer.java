package ru.bsuir.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bsuir.entity.CPost;

@Service
public class KafkaPostConsumer {

    @KafkaListener(topics = "posts-topic", groupId = "post-group")
    public void consumePost(CPost post) {
        System.out.println("Request post: " + post);
    }
}
