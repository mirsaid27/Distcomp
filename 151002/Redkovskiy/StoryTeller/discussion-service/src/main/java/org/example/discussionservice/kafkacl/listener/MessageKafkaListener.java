package org.example.discussionservice.kafkacl.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.example.discussionservice.kafkacl.request.KafkaRequestDto;
import org.example.discussionservice.kafkacl.response.KafkaResponseDto;
import org.example.discussionservice.service.MessageService;
import org.example.discussionservice.util.ObjectMapperProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MessageKafkaListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final MessageService messageService;

    @Autowired
    public MessageKafkaListener(KafkaTemplate<String, String> kafkaTemplate, MessageService messageService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageService = messageService;
    }

    @KafkaListener(topics = "inTopic", groupId = "groupId1")
    public void listen(@Payload String payload) {

        KafkaRequestDto request = ObjectMapperProvider.parseJsonStringToObject(payload, KafkaRequestDto.class);

        String responseStr = messageService.getProcessors().get(request.getRequestType())
                .process(request.getValue());

        KafkaResponseDto responseDto = new KafkaResponseDto(request.getRequestId(), responseStr);

        kafkaTemplate.send("outTopic", ObjectMapperProvider.writeObjectAsString(responseDto));

    }


}
