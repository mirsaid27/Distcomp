package org.example.tweetapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tweetapi.model.dto.request.AuthorRequestTo;
import org.example.tweetapi.model.dto.response.AuthorResponseTo;
import org.example.tweetapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseTo>> getAllAuthors() {
        List<AuthorResponseTo> authorResponseTos = authorService.getAllAuthors();
        return new ResponseEntity<>(authorResponseTos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseTo> createAuthor(@RequestBody @Valid AuthorRequestTo authorRequestDto) {
        AuthorResponseTo createdAuthor = authorService.createAuthor(authorRequestDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    // Получение пользователя по id
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseTo> getAuthorById(@PathVariable Long id) {
        AuthorResponseTo author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Обновление пользователя по id
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseTo> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorRequestTo authorRequestDto) {
        AuthorResponseTo updatedAuthor = authorService.updateAuthor(id, authorRequestDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AuthorResponseTo> updateUAuthor(@RequestBody @Valid AuthorRequestTo authorRequestDto) {
        if (authorRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AuthorResponseTo updatedAuthor = authorService.updateAuthor(authorRequestDto.getId(), authorRequestDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    // Удаление пользователя по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!authorService.existsById(id)) {
            // Если пользователь с таким ID не найден, вернуть 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
