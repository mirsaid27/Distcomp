package org.example.tweetapi.service;

import lombok.AllArgsConstructor;
import org.example.tweetapi.mapper.AuthorMapper;
import org.example.tweetapi.model.dto.request.AuthorRequestTo;
import org.example.tweetapi.model.dto.response.AuthorResponseTo;
import org.example.tweetapi.model.entity.Author;
import org.example.tweetapi.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    // Создать нового пользователя
    public AuthorResponseTo createAuthor(AuthorRequestTo authorRequestDto) {
        Author author = authorMapper.toEntity(authorRequestDto);
        author = authorRepository.save(author);
        return authorMapper.toResponse(author);
    }

    // Получить пользователя по id
    public AuthorResponseTo getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.map(authorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    // Получить всех пользователей
    public List<AuthorResponseTo> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    public AuthorResponseTo updateAuthor(Long id, AuthorRequestTo authorRequestDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        // Проверка длины логина
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


    // Удалить пользователя по id
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }
}
