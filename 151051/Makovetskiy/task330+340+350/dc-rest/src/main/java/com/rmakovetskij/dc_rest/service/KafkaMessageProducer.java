package com.rmakovetskij.dc_rest.service;

import com.rmakovetskij.dc_rest.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaMessageProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message) {
        kafkaTemplate.send("messages-topic", message);
    }
}
