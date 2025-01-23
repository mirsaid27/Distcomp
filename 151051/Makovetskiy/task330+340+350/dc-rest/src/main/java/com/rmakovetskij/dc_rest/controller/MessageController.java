package com.rmakovetskij.dc_rest.controller;

import com.rmakovetskij.dc_rest.model.dto.requests.MessageRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.MessageResponseTo;
import com.rmakovetskij.dc_rest.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody @Valid MessageRequestTo messageRequestDto) {
        MessageResponseTo createdMessage = messageService.createMessage(messageRequestDto);

        try {
            restTemplate.postForEntity("http://localhost:24130/api/v1.0/messages", createdMessage, MessageResponseTo.class);
        } catch (RestClientException e) {
            System.err.println("Ошибка при отправке комментария во второй модуль: " + e.getMessage());
        }
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAllMessages() {
        List<MessageResponseTo> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessageById(@PathVariable Long id) {
        MessageResponseTo message = messageService.getMessageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseTo> updateMessage(@PathVariable Long id, @RequestBody @Valid MessageRequestTo messageRequestDto) {
        MessageResponseTo updatedMessage = messageService.updateMessage(id, messageRequestDto);
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessageWithoutId(@RequestBody @Valid MessageRequestTo messageRequestDto) {
        Long id = messageRequestDto.getId();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MessageResponseTo updatedMessage = messageService.updateMessage(id, messageRequestDto);

        try{
            restTemplate.put("http://localhost:24130/api/v1.0/messages/" + id, messageRequestDto);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        try {
            messageService.deleteMessage(id);
            try {
                restTemplate.delete("http://localhost:24130/api/v1.0/messages/" + id);
            }
            catch (Exception ignored) {}

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}