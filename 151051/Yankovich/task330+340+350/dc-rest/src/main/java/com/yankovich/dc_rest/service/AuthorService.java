package com.yankovich.dc_rest.service;

import com.yankovich.dc_rest.mapper.AuthorMapper;
import com.yankovich.dc_rest.model.Author;
import com.yankovich.dc_rest.model.dto.requests.AuthorRequestTo;
import com.yankovich.dc_rest.model.dto.responses.AuthorResponseTo;
import com.yankovich.dc_rest.repository.interfaces.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @CacheEvict(value = "authors", allEntries = true)
    public AuthorResponseTo createAuthor(AuthorRequestTo authorRequestDto) {
            Author author = authorMapper.toEntity(authorRequestDto);
            author = authorRepository.save(author);
            return authorMapper.toResponse(author);
    }

    @Cacheable(value = "authors", key = "#id")
    public AuthorResponseTo getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.map(authorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Cacheable(value = "authorsList")
    public List<AuthorResponseTo> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = {"authors", "authorsList"}, key = "#id", allEntries = true)
    public AuthorResponseTo updateAuthor(Long id, AuthorRequestTo authorRequestDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        if (authorRequestDto.getLogin().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login must be at least 2 characters long");
        }

        existingAuthor.setLogin(authorRequestDto.getLogin());
        existingAuthor.setPassword(authorRequestDto.getPassword());
        existingAuthor.setFirstname(authorRequestDto.getFirstname());
        existingAuthor.setLastname(authorRequestDto.getLastname());

        authorRepository.save(existingAuthor);
        return authorMapper.toResponse(existingAuthor);
    }

    @CacheEvict(value = {"authors", "authorsList"}, key = "#id", allEntries = true)
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }
}