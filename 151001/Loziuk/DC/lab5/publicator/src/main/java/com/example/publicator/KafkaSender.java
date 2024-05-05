package com.example.publicator;

import com.example.publicator.dto.NoteRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteRequestTo> kafkaTemplate;

    public void sendMessage(NoteRequestTo message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }
}
