package by.bsuir.news.service;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.Note;
import by.bsuir.news.event.InTopicEvent;
import by.bsuir.news.event.InTopicMessage;
import by.bsuir.news.event.OutTopicEvent;
import by.bsuir.news.event.OutTopicMessage;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaServer {
    @Value("${spring.kafka.topics.in-topic}")
    public final String inTopic = "inTopic";
    @Value("${spring.kafka.topics.out-topic}")
    public final String outTopic = "outTopic";
    private final NoteService noteService;
    private final NoteRepository noteRepository;
    private final KafkaTemplate<String, OutTopicEvent> kafkaTemplate;

    @KafkaListener(topics = inTopic, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void process(ConsumerRecord<String, InTopicEvent> record) {
        final String key = record.key();
        final InTopicEvent event = record.value();
        final InTopicMessage message = event.message();
        final NoteRequestTo request = message.request();
        log.info(inTopic + ": key = " + key + "; operation = " + message.operation() + "; data = " + message.request());
        List<NoteResponseTo> resultList = switch(message.operation()) {
            case CREATE -> create(request);
            case GET_BY_ID -> getById(request.getId());
            case GET_ALL -> getAll();
            case UPDATE -> update(request);
            case DELETE -> delete(request.getId());
        };
        log.info(outTopic + ": key = " + key + "; data = " + resultList);
        kafkaTemplate.send(outTopic, key, new OutTopicEvent(event.id(), new OutTopicMessage(resultList)));
    }

    private List<NoteResponseTo> create(NoteRequestTo request) {
        return Collections.singletonList(noteService.create(request));
    }

    private List<NoteResponseTo> getById(Long id) {
        try {
            NoteResponseTo response = noteService.getById(id);
            return Collections.singletonList(response);
        }
        catch(ClientException ce) {
            return Collections.emptyList();
        }
    }

    private List<NoteResponseTo> getAll() {
        return noteService.getAll();
    }

    private List<NoteResponseTo> update(NoteRequestTo request) {
        try {
            NoteResponseTo response = noteService.update(request);
            return Collections.singletonList(response);
        }
        catch(ClientException ce) {
            return Collections.emptyList();
        }
    }

    private List<NoteResponseTo> delete(Long id) {
        Optional<Note> note = noteRepository.findByKeyId(id);
        if(note.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            Long response = noteService.delete(id);
            return Collections.singletonList(NoteResponseTo.toResponse(note.get()));
        }
        catch(ClientException ce) {
            return Collections.emptyList();
        }
    }
}

