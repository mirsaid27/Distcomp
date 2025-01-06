package org.example.tweetapi.service;

import lombok.AllArgsConstructor;
import org.example.tweetapi.mapper.AuthorMapper;
import org.example.tweetapi.model.dto.response.AuthorResponseTo;
import org.example.tweetapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponseTo> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .toList();
    }
}
