package by.bsuir.test_rw.repository.implementations.in_memory;

import by.bsuir.test_rw.model.entity.implementations.Note;
import by.bsuir.test_rw.repository.interfaces.NoteRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NoteInMemoryRepo extends InMemoryRepoLongId<Note> implements NoteRepository {
}
