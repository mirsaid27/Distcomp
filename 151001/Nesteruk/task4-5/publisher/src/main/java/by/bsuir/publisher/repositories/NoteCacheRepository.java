package by.bsuir.publisher.repositories;

import by.bsuir.publisher.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteCacheRepository extends CrudRepository<Note, Long> {
}
