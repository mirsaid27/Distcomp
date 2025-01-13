package by.bsuir.resttask.repository.inmemory;

import by.bsuir.resttask.entity.Author;
import by.bsuir.resttask.repository.AuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("in-memory-repositories")
public class AuthorInMemoryRepository extends InMemoryRepository<Author>
        implements AuthorRepository {

}
