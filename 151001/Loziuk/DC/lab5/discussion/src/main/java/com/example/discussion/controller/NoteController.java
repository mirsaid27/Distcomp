package com.example.discussion.controller;

import com.example.discussion.KafkaSender;
import com.example.discussion.dto.NoteRequestTo;
import com.example.discussion.dto.NoteResponseTo;
import com.example.discussion.exception.NotFoundException;
import com.example.discussion.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/")
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    private KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup", containerFactory = "noteRequestToConcurrentKafkaListenerContainerFactory")
    void listener(@Payload NoteRequestTo noteRequestTo){
        System.out.println(noteRequestTo.toString());
        if(Objects.equals(noteRequestTo.getMethod(), "GET")){
            //if(noteRequestTo.getId() != null){

            //}
            //else{
            NoteResponseTo msg= readById(noteRequestTo.getId());
            kafkaSender.sendMessage(msg, topic);
            if(msg != null)
                System.out.println(msg.toString());
            else
                System.out.println("null");
            // }
        }
        else if(Objects.equals(noteRequestTo.getMethod(), "POST")){
            NoteResponseTo msg= create(noteRequestTo.getCountry(),noteRequestTo);
            kafkaSender.sendMessage(msg, topic);
            System.out.println(msg.toString());
        }
        else if (Objects.equals(noteRequestTo.getMethod(), "PUT")){
            NoteResponseTo msg= update(noteRequestTo.getCountry(),noteRequestTo);
            kafkaSender.sendMessage(msg, topic);
            System.out.println(msg.toString());
        }
        else if(Objects.equals(noteRequestTo.getMethod(), "DELETE")){
            NoteResponseTo msg= delete(noteRequestTo.getId());
            kafkaSender.sendMessage(msg, topic);
            //System.out.println(msg.toString());
        }
    }

    @PostMapping(value = "notes")
    public NoteResponseTo create(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader,@RequestBody NoteRequestTo noteRequestTo) {
        return noteService.create(noteRequestTo, acceptLanguageHeader);
    }

    @GetMapping(value = "notes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read()
    {
        final List<NoteResponseTo> list = noteService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "notes/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(@PathVariable(name = "id") int id)
    {
        final NoteResponseTo note = noteService.read(id);
        if(note == null){
            return new ResponseEntity<>(new NotFoundException("Note not found", 404), HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(note, HttpStatus.OK);
    }

    public NoteResponseTo readById(int id) {
        return noteService.read(id);
    }

    @PutMapping(value = "notes")
    public NoteResponseTo update(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader, @RequestBody NoteRequestTo noteRequestTo) {
        return noteService.update(noteRequestTo, acceptLanguageHeader);
    }

    @DeleteMapping(value = "notes/{id}")
    public NoteResponseTo delete(@PathVariable(name = "id") int id) {
        boolean res = noteService.delete(id);
        if(res)
            return new NoteResponseTo();
        else
            return null;
    }
}
