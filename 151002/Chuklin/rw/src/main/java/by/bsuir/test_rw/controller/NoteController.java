package by.bsuir.test_rw.controller;

import by.bsuir.test_rw.kafka.response.KafkaResponse;
import by.bsuir.test_rw.model.dto.note.NoteRequestTO;
import by.bsuir.test_rw.model.dto.note.NoteResponseTO;
import by.bsuir.test_rw.service.db_interaction.interfaces.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping()
    public ResponseEntity<?> createNote(@RequestBody @Valid NoteRequestTO noteRequestTO) throws JsonProcessingException {
        KafkaResponse response = noteService.save(noteRequestTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getResponseObj());
    }

    @GetMapping()
    public ResponseEntity<List<NoteResponseTO>> receiveAllNotes() throws JsonProcessingException {
        KafkaResponse response = noteService.findAll();
        List<NoteResponseTO> responseList = mapper.
                readValue(response.getResponseObj(), new TypeReference<List<NoteResponseTO>>() {});
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTO> receiveNoteById(@PathVariable Long id) throws JsonProcessingException {
        KafkaResponse note = noteService.findById(id);
        NoteResponseTO noteResponseTO = mapper.readValue(note.getResponseObj(),NoteResponseTO.class);
        return ResponseEntity.ok(noteResponseTO);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTO> updateNote(@RequestBody @Valid NoteRequestTO noteRequestTO) throws JsonProcessingException {
        KafkaResponse note = noteService.update(noteRequestTO);
        NoteResponseTO noteResponseTO = mapper.readValue(note.getResponseObj(), NoteResponseTO.class);
        return ResponseEntity.ok(noteResponseTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) throws JsonProcessingException {
        noteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
