package by.bsuir.discussion.service;

import by.bsuir.discussion.model.entity.Note;
import by.bsuir.discussion.model.response.NoteResponseTo;
import by.bsuir.discussion.event.*;
import by.bsuir.discussion.model.entity.NoteState;
import by.bsuir.discussion.dao.NoteRepository;
import by.bsuir.discussion.service.mapper.NoteKafkaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaNoteServer {
    public static final String IN_TOPIC = "in-topic";
    public static final String OUT_TOPIC = "out-topic";
    private final NoteKafkaMapper noteKafkaMapper;
    private final NoteService noteService;
    private final NoteRepository noteRepository;
    private final KafkaTemplate<String, OutTopicEvent> kafkaTemplate;

    @KafkaListener(topics = IN_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void process(ConsumerRecord<String, InTopicEvent> record) {
        final String key = record.key();
        final InTopicEvent inTopicEvent = record.value();
        final InTopicMessage inTopicMsg = inTopicEvent.message();
        final NoteInTopicTo inTopicNote = inTopicMsg.noteDto();
        log.info(IN_TOPIC + ": key = " + key + "; operation = " + inTopicMsg.operation() + "; data = " + inTopicMsg.noteDto());
        List<NoteOutTopicTo> resultList = switch (inTopicMsg.operation()) {
            case GET_ALL -> noteKafkaMapper.responseDtoToOutTopicDto(noteService.findAll());
            case GET_BY_ID -> getById(inTopicNote.id());
            case DELETE_BY_ID -> delete(inTopicNote.id());
            case CREATE -> create(inTopicNote);
            case UPDATE -> update(inTopicNote);
        };
        log.info(OUT_TOPIC + ": key = " + key + "; data = " + resultList);
        kafkaTemplate.send(
                OUT_TOPIC,
                key,
                new OutTopicEvent(
                        inTopicEvent.id(),
                        new OutTopicMessage(
                                resultList
                        )
                )
        );
    }

    private List<NoteOutTopicTo> getById(Long id) {
        NoteResponseTo responseDto;
        try {
            responseDto = noteService.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return Collections.singletonList(noteKafkaMapper.responseDtoToOutTopicDto(responseDto));
    }

    private List<NoteOutTopicTo> create(NoteInTopicTo dto) {
        Note newEntity = noteKafkaMapper.toEntity(dto);
        newEntity.setState(NoteState.APPROVE);
        try {
            return Collections.singletonList(noteKafkaMapper.entityToDto(noteRepository.save(newEntity)));
        } catch (Exception ex) {
            newEntity.setState(NoteState.DECLINE);
            return Collections.emptyList();
        }
    }

    private List<NoteOutTopicTo> update(NoteInTopicTo dto) {
        Optional<Note> optionalNote = noteRepository.findByKeyId(dto.id());
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Note entity = optionalNote.get();
        final Note updated = noteKafkaMapper.partialUpdate(dto, entity);
        return Collections.singletonList(noteKafkaMapper.entityToDto(noteRepository.save(updated)));
    }

    private List<NoteOutTopicTo> delete(Long id) {
        Optional<Note> optionalNote = noteRepository.findByKeyId(id);
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Note deleted = optionalNote.get();
        noteRepository.delete(deleted);
        return Collections.singletonList(noteKafkaMapper.entityToDto(deleted));
    }
}