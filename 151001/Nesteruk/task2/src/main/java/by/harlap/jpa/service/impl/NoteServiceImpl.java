package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Note;
import by.harlap.jpa.repository.impl.NoteRepository;
import by.harlap.jpa.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Note with id '%d' doesn't exist";
    private final NoteRepository noteRepository;

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> {
            final String message = NOTE_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        noteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));

        noteRepository.deleteById(id);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note update(Note note) {
        noteRepository.findById(note.getId()).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));

        return noteRepository.save(note);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }
}

