package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Note;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.service.NoteService;
import by.harlap.rest.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Note with id '%d' doesn't exist";
    private final AbstractRepository <Note, Long> noteRepository;
    private final TweetService tweetService;

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
        tweetService.findById(note.getTweetId());
        return noteRepository.save(note);
    }

    @Override
    public Note update(Note note) {tweetService.findById(note.getTweetId());
        noteRepository.findById(note.getId()).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));
        tweetService.findById(note.getTweetId());

        return noteRepository.update(note);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }
}

