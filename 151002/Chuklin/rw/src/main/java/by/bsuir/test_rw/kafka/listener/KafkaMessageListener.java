package by.bsuir.test_rw.kafka.listener;

import by.bsuir.test_rw.kafka.RequestType;
import by.bsuir.test_rw.kafka.response.KafkaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Getter
@AllArgsConstructor
public class KafkaMessageListener {
    private final Map<RequestType, KafkaResponse> responseMap = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "OutTopic", groupId = "myGroup")
    public void listen(@Payload String payload) throws JsonProcessingException {
        KafkaResponse response = mapper.readValue(payload, KafkaResponse.class);
        responseMap.put(response.getType(), response);
    }
}
