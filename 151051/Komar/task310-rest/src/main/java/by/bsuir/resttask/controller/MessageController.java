package by.bsuir.resttask.controller;

import by.bsuir.resttask.dto.request.MessageRequestTo;
import by.bsuir.resttask.dto.response.MessageResponseTo;
import by.bsuir.resttask.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService MESSAGE_SERVICE;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo getById(@PathVariable Long id) {
        return MESSAGE_SERVICE.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseTo> getAll() {
        return MESSAGE_SERVICE.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseTo save(@RequestBody @Valid MessageRequestTo message) {
        return MESSAGE_SERVICE.save(message);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo update(@RequestBody @Valid MessageRequestTo message) {
        return MESSAGE_SERVICE.update(message);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        MESSAGE_SERVICE.delete(id);
    }
}
