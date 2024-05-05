package by.bsuir.test_rw.service.db_interaction.interfaces;

import by.bsuir.test_rw.kafka.response.KafkaResponse;
import by.bsuir.test_rw.model.dto.note.NoteRequestTO;
import by.bsuir.test_rw.model.entity.implementations.Note;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NoteService {
    KafkaResponse findById(Long id) throws JsonProcessingException;

    KafkaResponse findAll() throws JsonProcessingException;

    KafkaResponse save(NoteRequestTO note) throws JsonProcessingException;

    void deleteById(Long id) throws JsonProcessingException;

    KafkaResponse update(NoteRequestTO note) throws JsonProcessingException;
}
