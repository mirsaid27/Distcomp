package by.bsuir.dc.impl.author;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.author.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepository extends AbstractMemoryRepository<Author> {

    @Override
    public Optional<Author> update(Author author) {
        long id = author.getId();
        if(Objects.isNull(map.get(id))) {
            throw new NoSuchElementException("update failed");
        }
        Author memoryAuthor = map.get(id);
        Optional.of(author.getLogin()).ifPresent(memoryAuthor::setLogin);
        Optional.of(author.getPassword()).ifPresent(memoryAuthor::setPassword);
        Optional.of(author.getFirstname()).ifPresent(memoryAuthor::setFirstname);
        Optional.of(author.getLastname()).ifPresent(memoryAuthor::setLastname);

        map.put(id, memoryAuthor);
        return Optional.of(memoryAuthor);
    }
    @Override
    public boolean deleteById(long id) {
        return map.remove(id) != null;
    }
}
