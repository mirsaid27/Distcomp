package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "noteRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithNoteConverter(@Payload NoteRequestTo noteRequestTo) {
        if (Objects.equals(noteRequestTo.getMethod(), "GET")) {
            if ((Integer)noteRequestTo.getId() != null) {
                kafkaSender.sendCustomNote(getNote((int)noteRequestTo.getId()), topic);
            } else {
               // kafkaSender.sendCustomNote(getNotes());
            }
        } else {
            if (Objects.equals(noteRequestTo.getMethod(), "DELETE")) {
                kafkaSender.sendCustomNote(deleteNote(noteRequestTo.getId()), topic);
            } else {
                if (Objects.equals(noteRequestTo.getMethod(), "POST")) {
                    kafkaSender.sendCustomNote(saveNote(noteRequestTo.getCountry(), noteRequestTo), topic);
                } else {
                    if (Objects.equals(noteRequestTo.getMethod(), "PUT")) {
                        kafkaSender.sendCustomNote(updateNote(noteRequestTo.getCountry(), noteRequestTo), topic);
                    }
                }
            }
        }
    }

    @GetMapping
    public List<NoteResponseTo> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping("/{id}")
    public NoteResponseTo getNote(@PathVariable int id) {
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    public NoteResponseTo deleteNote(@PathVariable int id) {
        noteService.deleteNote(id);
        return new NoteResponseTo();
    }

    @PostMapping
    public NoteResponseTo saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) {
        return noteService.saveNote(note, acceptLanguageHeader);
    }

    @PutMapping()
    public NoteResponseTo updateNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) {
        return noteService.updateNote(note, acceptLanguageHeader);
    }

    @GetMapping("/byTweet/{id}")
    public List<NoteResponseTo> getUserByTweetId(@PathVariable int id) {
        return noteService.getNoteByTweetId(id);
    }
}
