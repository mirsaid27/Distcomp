package com.bsuir.dc.dao;

import com.bsuir.dc.model.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAuthorDao {
    private final Map<Long, Author> authors = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Author save(Author author) {
        if (author.getId() == 0) {
            author.setId(idGenerator.getAndIncrement());
        }
        authors.put(author.getId(), author);
        return author;
    }

    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(authors.get(id));
    }
    public Optional<Author> findByLogin(String login) {
        return authors.values()
                .stream()
                .filter(author -> author.getLogin().equals(login))
                .findFirst();
    }

    public void deleteById(long id) {authors.remove(id);}
}
