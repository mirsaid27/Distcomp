package by.bsuir.news.service;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.Note;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    private static final AtomicLong ids = new AtomicLong(1);

    public NoteResponseTo create(@Valid NoteRequestTo request) {
        Note note = NoteRequestTo.fromRequest(request);
        note.getKey().setId(ids.getAndIncrement());
        return NoteResponseTo.toResponse(noteRepository.save(note));
    }

    public List<NoteResponseTo> getAll() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream().map(NoteResponseTo::toResponse).collect(Collectors.toList());
    }

    public NoteResponseTo getById(Long id) throws ClientException {
        Optional<Note> note = noteRepository.findByKeyId(id);
        if(note.isPresent()) {
            return NoteResponseTo.toResponse(note.get());
        }
        throw new ClientException("Note not found");
    }

    public NoteResponseTo update(@Valid NoteRequestTo request) throws ClientException {
        if(noteRepository.findByKeyId(request.getId()).isEmpty()) {
            throw new ClientException("Note doesn't exist");
        }
        Note note = NoteRequestTo.fromRequest(request);
        noteRepository.save(note);
        return NoteResponseTo.toResponse(note);
    }

    public Long delete(Long id) throws ClientException {
        if(noteRepository.findByKeyId(id).isEmpty()) {
            throw new ClientException("Note doesn't exist");
        }
        noteRepository.delete(noteRepository.findByKeyId(id).get());
        if(noteRepository.findByKeyId(id).isPresent()) {
            throw new ClientException("Failed to delete the note");
        }
        return id;
    }
}
