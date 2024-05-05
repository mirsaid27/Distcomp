package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo2;
import by.bsuir.dto.NoteResponseTo2;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.repository.StoryRepository;
import by.bsuir.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {
    @Autowired
    private RestClient restClient;
    @Autowired
    private NoteService noteService;

    private String uriBase = "http://localhost:24130/api/v1.0/notes";

    @GetMapping
    public ResponseEntity<List<?>> getNotes() {
        //kafkaSender.sendCustomMessage();
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo2> getNote(@PathVariable Long id) throws NotFoundException {
        NoteResponseTo2 note = noteService.getNote(id);
        return ResponseEntity.status(200).body(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) throws NotFoundException {
        NoteResponseTo2 note = noteService.deleteNote(id);
        return ResponseEntity.status(note == null ? HttpStatus.NOT_FOUND : HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping
    public ResponseEntity<NoteResponseTo2> saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo2 note) throws NotFoundException {
        NoteResponseTo2 newNote = noteService.saveNote(acceptLanguageHeader, note);
        return ResponseEntity.status(201).body(newNote);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTo2> updateNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo2 note) throws NotFoundException {
        NoteResponseTo2 updatedNote = noteService.updateNote(acceptLanguageHeader, note);
        return ResponseEntity.status(200).body(updatedNote);
    }

//    @GetMapping("/byStory/{id}")
//    public ResponseEntity<?> getAuthorByStoryId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
//        return restClient.get()
//                .uri(uriBase + "/byStory/" + id)
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .retrieve()
//                .body(ResponseEntity.class);
//    }

}
