package by.bsuir.news.controller;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService = new NoteService();

    @GetMapping
    public ResponseEntity getAllNotes() {
        try {
            return new ResponseEntity(noteService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@PathVariable Long id) {
        try {
            return new ResponseEntity<NoteResponseTo>(noteService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<NoteResponseTo>(new NoteResponseTo(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity saveNote(@Valid @RequestBody NoteRequestTo note) {
        try {
            return new ResponseEntity(noteService.create(note), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().header("Failed to create the note").body(note);
        }
    }

    @PutMapping
    public ResponseEntity updateNote(@Valid @RequestBody NoteRequestTo note) {
        try {
            return new ResponseEntity(noteService.update(note), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable Long id) {
        try {
            return new ResponseEntity(noteService.delete(id), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
