package by.bsuir.services;

import by.bsuir.dao.NoteDao;
import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.NoteListMapper;
import by.bsuir.mapper.NoteMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class NoteService {
    @Autowired
    NoteMapper NoteMapper;
    @Autowired
    NoteDao NoteDao;
    @Autowired
    NoteListMapper NoteListMapper;

    public NoteResponseTo getNoteById(@Min(0) Long id) throws NotFoundException {
        Optional<Note> Note = NoteDao.findById(id);
        return Note.map(value -> NoteMapper.NoteToNoteResponse(value)).orElseThrow(() -> new NotFoundException("Note not found!", 40004L));
    }

    public List<NoteResponseTo> getNotes() {
        return NoteListMapper.toNoteResponseList(NoteDao.findAll());
    }

    public NoteResponseTo saveNote(@Valid NoteRequestTo Note) {
        Note NoteToSave = NoteMapper.NoteRequestToNote(Note);
        return NoteMapper.NoteToNoteResponse(NoteDao.save(NoteToSave));
    }

    public void deleteNote(@Min(0) Long id) throws DeleteException {
        NoteDao.delete(id);
    }

    public NoteResponseTo updateNote(@Valid NoteRequestTo Note) throws UpdateException {
        Note NoteToUpdate = NoteMapper.NoteRequestToNote(Note);
        return NoteMapper.NoteToNoteResponse(NoteDao.update(NoteToUpdate));
    }
}
