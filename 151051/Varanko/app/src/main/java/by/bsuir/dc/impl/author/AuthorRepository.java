package by.bsuir.dc.impl.author;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.author.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends AbstractMemoryRepository<Author> {
}
