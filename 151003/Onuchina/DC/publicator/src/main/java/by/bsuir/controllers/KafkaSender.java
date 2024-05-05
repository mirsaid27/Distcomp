package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, NoteRequestTo2> userKafkaTemplate;


    public void sendCustomMessage(NoteRequestTo2 noteRequestTo2, String topicName) {
        userKafkaTemplate.send(topicName, noteRequestTo2);
    }
}
