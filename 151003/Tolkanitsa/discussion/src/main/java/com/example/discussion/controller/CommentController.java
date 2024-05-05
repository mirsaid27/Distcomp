package com.example.discussion.controller;

import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.kafka.KafkaSender;
import com.example.discussion.model.request.CommentRequestTo;
import com.example.discussion.model.response.CommentResponseTo;
import com.example.discussion.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Autowired
     KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "commentRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload CommentRequestTo commentRequestTo) {
        if (Objects.equals(commentRequestTo.getMethod(), "GET")) {
            if (commentRequestTo.getId() != null) {
                kafkaSender.sendCustomMessage(getNote(commentRequestTo.getId()), topic);
            } else {
                // kafkaSender.sendCustomMessage(getComments());
            }
        } else {
            if (Objects.equals(commentRequestTo.getMethod(), "DELETE")) {
                kafkaSender.sendCustomMessage(deleteNote(commentRequestTo.getId()), topic);
            } else {
                if (Objects.equals(commentRequestTo.getMethod(), "POST")) {
                    kafkaSender.sendCustomMessage(saveNote(commentRequestTo.getCountry(), commentRequestTo), topic);
                } else {
                    if (Objects.equals(commentRequestTo.getMethod(), "PUT")) {
                        kafkaSender.sendCustomMessage(updateComment(commentRequestTo.getCountry(), commentRequestTo), topic);
                    }
                }
            }
        }
    }

    @GetMapping
    public List<CommentResponseTo> getComments() {
        return commentService.getNotes();
    }

    @DeleteMapping("/{id}")
    public CommentResponseTo deleteNote(@PathVariable Long id) {
        commentService.deleteNote(id);
        return new CommentResponseTo();
    }

    @GetMapping("/{id}")
    public CommentResponseTo getNote(@PathVariable Long id) {
        return commentService.getNoteById(id);
    }

    @PostMapping
    public CommentResponseTo saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                      @RequestBody CommentRequestTo commentRequestTo) {

        return commentService.saveNote(commentRequestTo, acceptLanguageHeader);
    }

    @PutMapping()
    public CommentResponseTo updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                           @RequestBody CommentRequestTo commentRequestTo) {
        return commentService.updateNote(commentRequestTo, acceptLanguageHeader);
    }


}