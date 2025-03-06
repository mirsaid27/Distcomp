package by.bsuir.distcomp.service;

import by.bsuir.distcomp.dto.mapper.AuthorMapper;
import by.bsuir.distcomp.dto.request.AuthorRequestTo;
import by.bsuir.distcomp.dto.response.AuthorResponseTo;
import by.bsuir.distcomp.entity.Author;
import by.bsuir.distcomp.repository.AuthorRepository;
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
        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }

    public AuthorResponseTo getAuthorById(Long id) {
        return authorMapper.toDto(authorRepository.findById(id));
    }

    public AuthorResponseTo createAuthor(AuthorRequestTo authorRequestTo) {
        return authorMapper.toDto(authorRepository.create(authorMapper.toEntity(authorRequestTo)));
    }

    public AuthorResponseTo updateAuthor(AuthorRequestTo authorRequestTo) {
        return authorMapper.toDto(authorRepository.update(authorMapper.toEntity(authorRequestTo)));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    
}
