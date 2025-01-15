package by.bsuir.dc.impl.note;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.note.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface NoteRepository extends AbstractMemoryRepository<Note> {
}
