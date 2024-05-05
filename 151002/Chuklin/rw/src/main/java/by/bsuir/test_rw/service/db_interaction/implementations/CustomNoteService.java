package by.bsuir.test_rw.service.db_interaction.implementations;

import by.bsuir.test_rw.kafka.RequestType;
import by.bsuir.test_rw.kafka.listener.KafkaMessageListener;
import by.bsuir.test_rw.kafka.request.KafkaRequest;
import by.bsuir.test_rw.kafka.response.KafkaResponse;
import by.bsuir.test_rw.model.dto.note.NoteRequestTO;
import by.bsuir.test_rw.service.db_interaction.interfaces.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomNoteService implements NoteService {

    private static final String TOPIC_NAME = "InTopic";
    private final KafkaMessageListener listener;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public KafkaResponse findById(Long id) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(RequestType.GET, List.of(mapper.writeValueAsString(id)));
        kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(request));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.warn("Interrupted");
        }
        return listener.getResponseMap().get(RequestType.GET);
    }

    @Override
    public KafkaResponse findAll() throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(RequestType.GET_ALL, Collections.emptyList());
        kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(request));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.warn("Interrupted");
        }
        return listener.getResponseMap().get(RequestType.GET_ALL);
    }

    @Override
    public KafkaResponse save(NoteRequestTO entity) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(RequestType.POST, List.of(mapper.writeValueAsString(entity)));
        try {
            kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(request));
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            log.warn("Interrupted");
        }
        return listener.getResponseMap().get(RequestType.POST);
    }

    @Override
    public void deleteById(Long id) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(RequestType.DELETE, List.of(mapper.writeValueAsString(id)));
        kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(request));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.warn("Interrupted");
        }
    }

    @Override
    public KafkaResponse update(NoteRequestTO entity) throws JsonProcessingException {
        KafkaRequest request = new KafkaRequest(RequestType.PUT, List.of(mapper.writeValueAsString(entity)));
        kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(request));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException){
            log.warn("Interrupted");
        }
        return listener.getResponseMap().get(RequestType.PUT);
    }
}
