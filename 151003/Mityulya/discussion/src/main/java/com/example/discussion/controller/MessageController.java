package com.example.discussion.controller;

import com.example.discussion.kafka.KafkaSender;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import com.example.discussion.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Autowired
     KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "commentRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload MessageRequestTo commentRequestTo) {
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
    public List<MessageResponseTo> getComments() {
        return messageService.getNotes();
    }

    @DeleteMapping("/{id}")
    public MessageResponseTo deleteNote(@PathVariable Long id) {
        messageService.deleteNote(id);
        return new MessageResponseTo();
    }

    @GetMapping("/{id}")
    public MessageResponseTo getNote(@PathVariable Long id) {
        return messageService.getNoteById(id);
    }

    @PostMapping
    public MessageResponseTo saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                      @RequestBody MessageRequestTo messageRequestTo) {
        return messageService.saveNote(messageRequestTo, acceptLanguageHeader);
    }

    @PutMapping()
    public MessageResponseTo updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                           @RequestBody MessageRequestTo messageRequestTo) {
        return messageService.updateNote(messageRequestTo, acceptLanguageHeader);
    }


}