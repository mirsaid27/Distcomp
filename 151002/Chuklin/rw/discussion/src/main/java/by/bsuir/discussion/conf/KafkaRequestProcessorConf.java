package by.bsuir.discussion.conf;

import by.bsuir.discussion.exception.model.not_found.EntityNotFoundException;
import by.bsuir.discussion.kafka.KafkaRequestProcessor;
import by.bsuir.discussion.kafka.RequestType;
import by.bsuir.discussion.model.dto.note.NoteRequestTO;
import by.bsuir.discussion.model.dto.note.NoteResponseTO;
import by.bsuir.discussion.model.entity.implementations.Note;
import by.bsuir.discussion.service.db_interaction.interfaces.NoteService;
import by.bsuir.discussion.service.interfaces.NoteToConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaRequestProcessorConf {

    private final NoteService noteService;
    private final NoteToConverter converter;
    private final ObjectMapper mapper = new ObjectMapper();

    @Bean
    public Map<RequestType, KafkaRequestProcessor> createProcessors() {
        Map<RequestType, KafkaRequestProcessor> res = new HashMap<>();
        res.put(RequestType.POST, (values)->createNote(values.get(0)));
        res.put(RequestType.GET_ALL, (values)->receiveAllNotes());
        res.put(RequestType.GET, (values)->receiveNoteById(values.get(0)));
        res.put(RequestType.PUT, (values)->update(values.get(0)));
        res.put(RequestType.DELETE, (values) -> deleteById(values.get(0)));
        return res;
    }

    private ResponseEntity<NoteResponseTO> createNote(String str) {
        NoteRequestTO noteRequestTO;
        try {
            noteRequestTO = mapper.readValue(str, NoteRequestTO.class);
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }
        Note note = converter.convertToEntity(noteRequestTO);
        noteService.save(note);
        NoteResponseTO noteResponseTO = converter.convertToDto(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteResponseTO);
    }

    private ResponseEntity<List<NoteResponseTO>> receiveAllNotes() {
        List<Note> notes = noteService.findAll();
        List<NoteResponseTO> responseList = notes.stream()
                .map(converter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    private ResponseEntity<NoteResponseTO> receiveNoteById(String str) {
        Long id = Long.valueOf(str);
        Note note;
        try {
            note = noteService.findById(id);
        } catch (Exception e){
            return ResponseEntity.status(404).build();
        }
        NoteResponseTO noteResponseTo = converter.convertToDto(note);
        return ResponseEntity.ok(noteResponseTo);
    }

    private ResponseEntity<NoteResponseTO> update(String str) {
        NoteRequestTO noteRequestTO;
        try {
            noteRequestTO = mapper.readValue(str, NoteRequestTO.class);
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }
        Note note = converter.convertToEntity(noteRequestTO);
        noteService.update(note);
        NoteResponseTO messageResponseTo = converter.convertToDto(note);
        return ResponseEntity.ok(messageResponseTo);
    }

    private ResponseEntity<Void> deleteById(String str) {
        Long id = Long.valueOf(str);
        try {
            noteService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
