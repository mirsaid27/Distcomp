package com.rmakovetskij.dc_rest.controller;

import com.rmakovetskij.dc_rest.model.dto.requests.MessageRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.MessageResponseTo;
import com.rmakovetskij.dc_rest.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Создать новый комментарий
    @PostMapping
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody @Valid MessageRequestTo messageRequestDto) {
        MessageResponseTo createdMessage = messageService.createMessage(messageRequestDto);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    // Получить все комментарии
    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAllMessages() {
        List<MessageResponseTo> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Получить комментарий по id
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessageById(@PathVariable Long id) {
        MessageResponseTo message = messageService.getMessageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Обновить комментарий по id
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseTo> updateMessage(@PathVariable Long id, @RequestBody @Valid MessageRequestTo messageRequestDto) {
        MessageResponseTo updatedMessage = messageService.updateMessage(id, messageRequestDto);
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessageWithoutId(@RequestBody @Valid MessageRequestTo messageRequestDto) {
        Long id = messageRequestDto.getId(); // Получаем id из запроса
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Возвращаем 400 если id отсутствует
        }

        MessageResponseTo updatedMessage = messageService.updateMessage(id, messageRequestDto);
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    // Удалить комментарий по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}