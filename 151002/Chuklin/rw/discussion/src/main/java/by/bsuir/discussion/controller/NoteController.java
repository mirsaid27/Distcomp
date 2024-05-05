package by.bsuir.discussion.controller;

import by.bsuir.discussion.exception.model.not_found.EntityNotFoundException;
import by.bsuir.discussion.model.dto.note.NoteRequestTO;
import by.bsuir.discussion.model.dto.note.NoteResponseTO;
import by.bsuir.discussion.model.entity.implementations.Note;
import by.bsuir.discussion.service.db_interaction.interfaces.NoteService;
import by.bsuir.discussion.service.interfaces.NoteToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService service;
    private final NoteToConverter converter;

    @PostMapping()
    public ResponseEntity<NoteResponseTO> createNote(@RequestBody @Valid NoteRequestTO messageRequestTo) {
        Note note = converter.convertToEntity(messageRequestTo);
        service.save(note);
        NoteResponseTO messageResponseTo = converter.convertToDto(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageResponseTo);
    }

    @GetMapping()
    public ResponseEntity<List<NoteResponseTO>> receiveAllNotes() {
        List<Note> notes = service.findAll();
        List<NoteResponseTO> responseList = notes.stream()
                .map(converter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTO> receiveNoteById(@PathVariable Long id) {
        Note note = service.findById(id);
        NoteResponseTO noteResponseTO = converter.convertToDto(note);
        return ResponseEntity.ok(noteResponseTO);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTO> updateNote(@RequestBody @Valid NoteRequestTO noteRequestTO) {
        Note note = converter.convertToEntity(noteRequestTO);
        service.update(note);
        NoteResponseTO noteResponseTO = converter.convertToDto(note);
        return ResponseEntity.ok(noteResponseTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
