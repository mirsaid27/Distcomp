package by.bsuir.news.controller;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.Note;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.service.GenericService;
import by.bsuir.news.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    @Value("${app.notes-path}")
    private String notesPath;
    //private final RestClient restClient;
    private final NoteService noteService;

    private static void AddHeadersWithoutLength(HttpHeaders httpHeaders, HttpHeaders added) {
        httpHeaders.addAll(added);
        httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseTo>> getAllNotes(@RequestHeader HttpHeaders headers) {
        try {
            return ResponseEntity.ok(noteService.getAll());
        }
        catch(ClientException ce) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(noteService.getById(id));
        }
        catch(ClientException ce) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<NoteResponseTo> saveNote(@RequestHeader HttpHeaders headers, @Valid @RequestBody NoteRequestTo note) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(note));
        }
        catch(ClientException ce) {
            return ResponseEntity.badRequest().body(new NoteResponseTo());
        }
    }

    @PutMapping
    public ResponseEntity<NoteResponseTo> updateNote(@RequestHeader HttpHeaders headers, @Valid @RequestBody NoteRequestTo note) {
        try {
            return ResponseEntity.ok(noteService.update(note));
        }
        catch(ClientException ce) {
            return ResponseEntity.badRequest().body(new NoteResponseTo());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNote(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(noteService.delete(id));
        }
        catch(ClientException ce) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
