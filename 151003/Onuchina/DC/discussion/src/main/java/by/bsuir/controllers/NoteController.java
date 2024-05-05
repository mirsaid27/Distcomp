package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.exceptions.DeleteException2;
import by.bsuir.services.NoteService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private KafkaSender2 kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "noteRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload NoteRequestTo noteRequestTo) {
        if (Objects.equals(noteRequestTo.getMethod(), "GET")) {
            if (noteRequestTo.getId() != null) {
                kafkaSender.sendCustomMessage(getNote(noteRequestTo.getId()), topic);
            }
        } else {
            if (Objects.equals(noteRequestTo.getMethod(), "DELETE")) {
                try {
                    kafkaSender.sendCustomMessage(deleteNote(noteRequestTo.getId()), topic);
                }
                catch (DeleteException2 e){
                    kafkaSender.sendCustomMessage(null, topic);
                }
            } else {
                if (Objects.equals(noteRequestTo.getMethod(), "POST")) {
                    try {
                        kafkaSender.sendCustomMessage(saveNote(noteRequestTo.getCountry(), noteRequestTo), topic);
                    } catch (Exception e) {
                        kafkaSender.sendCustomMessage(null, topic);
                    }
                } else {
                    if (Objects.equals(noteRequestTo.getMethod(), "PUT")) {
                        kafkaSender.sendCustomMessage(updateNote(noteRequestTo.getCountry(), noteRequestTo), topic);
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
    public NoteResponseTo getNote(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    public NoteResponseTo deleteNote(@PathVariable Long id) throws DeleteException2 {
        noteService.deleteNote(id);
        return new NoteResponseTo();
    }

    @PostMapping
    public NoteResponseTo saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) throws MethodArgumentNotValidException {
        return noteService.saveNote(note, acceptLanguageHeader);
    }

    @PutMapping()
    public NoteResponseTo updateNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) {
        return noteService.updateNote(note, acceptLanguageHeader);
    }

//    @GetMapping("/byStory/{id}")
//    public List<NoteResponseTo> getEditorByStoryId(@PathVariable Long id) {
//        return noteService.getNoteByStoryId(id);
//    }
}
