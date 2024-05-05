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
    NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseTo>> getNotes(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(noteService.getNotes(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@PathVariable Long id) {
        return ResponseEntity.status(200).body(noteService.getNoteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<NoteResponseTo> saveNote(@RequestBody NoteRequestTo Note) {
        NoteResponseTo savedNote = noteService.saveNote(Note);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTo> updateNote(@RequestBody NoteRequestTo Note) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.updateNote(Note));
    }

    @GetMapping("/byTweet/{id}")
    public ResponseEntity<List<NoteResponseTo>> getUserByTweetId(@PathVariable Long id) {
        return ResponseEntity.status(200).body(noteService.getNoteByTweetId(id));
    }
}
