package com.yankovich.dc_rest.controller;

import com.yankovich.dc_rest.model.dto.requests.AuthorRequestTo;
import com.yankovich.dc_rest.model.dto.responses.AuthorResponseTo;
import com.yankovich.dc_rest.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseTo> createAuthor(@RequestBody @Valid AuthorRequestTo authorRequestDto) {
            AuthorResponseTo createdAuthor = authorService.createAuthor(authorRequestDto);
            return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseTo> getAuthorById(@PathVariable Long id) {
        AuthorResponseTo author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseTo>> getAllAuthors() {
        List<AuthorResponseTo> authorsResponseDtos = authorService.getAllAuthors();
        return new ResponseEntity<>(authorsResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseTo> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorRequestTo authorRequestDto) {
        AuthorResponseTo updatedAuthor = authorService.updateAuthor(id, authorRequestDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AuthorResponseTo> updateAuthor(@RequestBody @Valid AuthorRequestTo authorRequestDto) {
        if (authorRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AuthorResponseTo updatedAuthor = authorService.updateAuthor(authorRequestDto.getId(), authorRequestDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
