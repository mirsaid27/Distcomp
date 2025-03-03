package com.bsuir.dc.service;

import com.bsuir.dc.dao.InMemoryAuthorDao;
import com.bsuir.dc.dao.InMemoryTopicDao;
import com.bsuir.dc.dto.request.AuthorRequestTo;
import com.bsuir.dc.dto.response.AuthorResponseTo;
import com.bsuir.dc.exception.DuplicateFieldException;
import com.bsuir.dc.exception.EntityNotFoundException;
import com.bsuir.dc.model.Author;
import com.bsuir.dc.model.Topic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final ModelMapper modelMapper;
    private final InMemoryAuthorDao authorDao;
    private final InMemoryTopicDao topicDao;

    @Autowired
    public AuthorService(ModelMapper modelMapper, InMemoryAuthorDao authorDao, InMemoryTopicDao topicDao) {
        this.modelMapper = modelMapper;
        this.authorDao = authorDao;
        this.topicDao = topicDao;
    }

    private Author convertToAuthor(AuthorRequestTo authorRequestTo) {
        return modelMapper.map(authorRequestTo, Author.class);
    }

    private AuthorResponseTo convertToResponse(Author author) {
        return modelMapper.map(author, AuthorResponseTo.class);
    }

    public AuthorResponseTo create(AuthorRequestTo authorRequestTo) {
        if (authorDao.findByLogin(authorRequestTo.getLogin()).isPresent()) {
            throw new DuplicateFieldException("login", authorRequestTo.getLogin());
        }

        Author author = convertToAuthor(authorRequestTo);
        authorDao.save(author);
        return convertToResponse(author);
    }

    public List<AuthorResponseTo> findAll() {
        return authorDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponseTo findById(long id) throws EntityNotFoundException {
        Author author = authorDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This author doesn't exist."));

        return convertToResponse(author);
    }

    public AuthorResponseTo findByTopicId(long topicId) throws EntityNotFoundException {
        Topic topic = topicDao.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Author with id " + topicId + " doesn't exist."));
        Author author = authorDao.findById(topic.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("No author found with topic id " + topicId));

        return convertToResponse(author);
    }

    public AuthorResponseTo update(AuthorRequestTo authorRequestTo) throws EntityNotFoundException, DuplicateFieldException {
        authorDao.findById(authorRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This author doesn't exist."));

        authorDao.findByLogin(authorRequestTo.getLogin())
                .filter(author -> author.getId() != authorRequestTo.getId())
                .ifPresent(author -> {
                    throw new DuplicateFieldException("login", authorRequestTo.getLogin());
                });

        Author updatedAuthor = convertToAuthor(authorRequestTo);
        authorDao.save(updatedAuthor);

        return convertToResponse(updatedAuthor);
    }

    public AuthorResponseTo partialUpdate(AuthorRequestTo authorRequestTo) throws EntityNotFoundException, DuplicateFieldException {
        Author author = authorDao.findById(authorRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        if (authorRequestTo.getLogin() != null && !authorRequestTo.getLogin().equals(author.getLogin())) {
            if (authorDao.findByLogin(authorRequestTo.getLogin()).isPresent()) {
                throw new DuplicateFieldException("login", authorRequestTo.getLogin());
            }
            author.setLogin(authorRequestTo.getLogin());
        }
        if (authorRequestTo.getFirstname() != null) {
            author.setFirstname(authorRequestTo.getFirstname());
        }
        if (authorRequestTo.getLastname() != null) {
            author.setLastname(authorRequestTo.getLastname());
        }
        if (authorRequestTo.getPassword() != null) {
            author.setPassword(authorRequestTo.getPassword());
        }
        authorDao.save(author);

        return convertToResponse(author);
    }

    public void delete(long id) throws EntityNotFoundException {
        authorDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This author doesn't exist."));
        authorDao.deleteById(id);
    }
}
