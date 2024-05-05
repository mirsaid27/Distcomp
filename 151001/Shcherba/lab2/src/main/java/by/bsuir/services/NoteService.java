package by.bsuir.services;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.entities.Note;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.NoteListMapper;
import by.bsuir.mapper.NoteMapper;
import by.bsuir.repository.NoteRepository;
import by.bsuir.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class NoteService {
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteRepository noteDao;
    @Autowired
    NoteListMapper noteListMapper;
    @Autowired
    TweetRepository tweetRepository;

    public NoteResponseTo getNoteById(@Min(0) Long id) throws NotFoundException {
        Optional<Note> note = noteDao.findById(id);
        return note.map(value -> noteMapper.noteToNoteResponse(value)).orElseThrow(() -> new NotFoundException("Note not found!", 40004L));
    }

    public List<NoteResponseTo> getNotes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Note> notes = noteDao.findAll(pageable);
        return noteListMapper.toNoteResponseList(notes.toList());
    }

    public NoteResponseTo saveNote(@Valid NoteRequestTo note) {
        Note noteToSave = noteMapper.noteRequestToNote(note);
        if (note.getTweetId()!=null) {
            noteToSave.setTweet(tweetRepository.findById(note.getTweetId()).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L)));
        }
        return noteMapper.noteToNoteResponse(noteDao.save(noteToSave));
    }

    public void deleteNote(@Min(0) Long id) throws DeleteException {
        if (!noteDao.existsById(id)) {
            throw new DeleteException("Note not found!", 40004L);
        } else {
            noteDao.deleteById(id);
        }
    }

    public NoteResponseTo updateNote(@Valid NoteRequestTo note) throws UpdateException {
        Note noteToUpdate = noteMapper.noteRequestToNote(note);
        if (!noteDao.existsById(note.getId())) {
            throw new UpdateException("Note not found!", 40004L);
        } else {
            if (note.getTweetId()!=null) {
                noteToUpdate.setTweet(tweetRepository.findById(note.getTweetId()).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L)));
            }
            return noteMapper.noteToNoteResponse(noteDao.save(noteToUpdate));
        }
    }

    public List<NoteResponseTo> getNoteByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        List<Note> note = noteDao.findNotesByTweet_Id(tweetId);
        if (note.isEmpty()){
            throw new NotFoundException("Note not found!", 40004L);
        }
        return noteListMapper.toNoteResponseList(note);
    }
}
