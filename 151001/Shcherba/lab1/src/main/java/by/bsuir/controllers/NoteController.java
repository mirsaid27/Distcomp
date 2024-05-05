package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {
    @Autowired
    NoteService NoteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseTo>> getNotes() {
        return ResponseEntity.status(200).body(NoteService.getNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@PathVariable Long id) {
        return ResponseEntity.status(200).body(NoteService.getNoteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        NoteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<NoteResponseTo> saveNote(@RequestBody NoteRequestTo Note) {
        NoteResponseTo savedNote = NoteService.saveNote(Note);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTo> updateNote(@RequestBody NoteRequestTo Note) {
        return ResponseEntity.status(HttpStatus.OK).body(NoteService.updateNote(Note));
    }
}
