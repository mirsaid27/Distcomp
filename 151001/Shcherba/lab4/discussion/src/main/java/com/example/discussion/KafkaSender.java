package com.example.discussion;

import com.example.discussion.dto.NoteResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteResponseTo> kafkaTemplate;

    public void sendNote(NoteResponseTo note, String topicName) {
        kafkaTemplate.send(topicName, note);
    }
}
