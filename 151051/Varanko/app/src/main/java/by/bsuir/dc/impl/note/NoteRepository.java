package by.bsuir.dc.impl.note;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.note.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoteRepository extends AbstractMemoryRepository<Note> {
    @Override
    public Optional<Note> update(Note note) {
        long id = note.getId();
        if(Objects.isNull(map.get(id))) {
            throw new NoSuchElementException("update failed");
        }
        Note memoryNote = map.get(id);
        Optional.of(note.getStoryId()).ifPresent(memoryNote::setStoryId);
        Optional.of(note.getContent()).ifPresent(memoryNote::setContent);

        map.put(id, memoryNote);
        return Optional.of(memoryNote);
    }
    @Override
    public boolean deleteById(long id) {
        return map.remove(id) != null;
    }
}
