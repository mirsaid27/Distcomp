package by.bsuir.discussion.service.db_interaction.implementations;

import by.bsuir.discussion.exception.model.not_found.EntityNotFoundException;
import by.bsuir.discussion.model.entity.implementations.Note;
import by.bsuir.discussion.repository.interfaces.NoteRepository;
import by.bsuir.discussion.service.db_interaction.interfaces.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CustomNoteService implements NoteService {

    private final AtomicLong id = new AtomicLong(0L);
    private final NoteRepository noteRepository;

    @Override
    @Cacheable(value = "note", key = "#id", unless = "#result==null")
    public Note findById(Long id) throws EntityNotFoundException {
        return noteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public void save(Note entity)  {
        entity.setId(id.incrementAndGet());
        noteRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        boolean exists = noteRepository.findById(id).isPresent();
        if (!exists) {
            throw new EntityNotFoundException(id);
        } else {
            noteRepository.deleteById(id);
        }
    }

    @Override
    public void update(Note entity) {
        boolean exists = noteRepository.findById(entity.getId()).isPresent();
        if (!exists) {
            throw new EntityNotFoundException(entity.getId());
        } else {
            noteRepository.save(entity);
        }
    }
}
