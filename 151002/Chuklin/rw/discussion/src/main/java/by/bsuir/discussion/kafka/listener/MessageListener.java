package by.bsuir.discussion.kafka.listener;

import by.bsuir.discussion.kafka.KafkaRequestProcessor;
import by.bsuir.discussion.kafka.RequestType;
import by.bsuir.discussion.kafka.request.KafkaRequest;
import by.bsuir.discussion.kafka.response.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageListener {
    private final Map<RequestType, KafkaRequestProcessor> processors;
    private final KafkaTemplate<String, String> template;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "InTopic", groupId = "myGroup")
    public void listen(@Payload String payload) throws JsonProcessingException {
        KafkaRequest request = objectMapper.readValue(payload, KafkaRequest.class);
        ResponseEntity<?> response = processors.get(request.getType()).process(request.getArgs());
        KafkaResponse kafkaResponse = new KafkaResponse(request.getType(), objectMapper.writeValueAsString(response.getBody()),response.getStatusCodeValue());
        template.send("OutTopic", objectMapper.writeValueAsString(kafkaResponse));
    }
}
