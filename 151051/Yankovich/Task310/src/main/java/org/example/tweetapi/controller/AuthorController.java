package org.example.tweetapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.tweetapi.model.dto.response.AuthorResponseTo;
import org.example.tweetapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseTo>> getAllUsers() {
        List<AuthorResponseTo> authorResponseTos = authorService.getAllAuthors();
        return new ResponseEntity<>(authorResponseTos, HttpStatus.OK);
    }
}
