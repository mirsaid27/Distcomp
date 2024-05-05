package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.mapper.NoteMapper;
import by.harlap.jpa.model.Note;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.service.NoteService;
import by.harlap.jpa.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteFacade {

    private final NoteService noteService;
    private final TweetService tweetService;
    private final NoteMapper noteMapper;

    @Transactional(readOnly = true)
    public NoteResponseDto findById(Long id) {
        Note note = noteService.findById(id);
        return noteMapper.toNoteResponse(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponseDto> findAll() {
        List<Note> notes = noteService.findAll();

        return notes.stream().map(noteMapper::toNoteResponse).toList();
    }

    @Transactional
    public NoteResponseDto save(CreateNoteDto noteRequest) {
        Note note = noteMapper.toNote(noteRequest);

        Tweet tweet = tweetService.findById(noteRequest.getTweetId());
        note.setTweet(tweet);

        Note savedNote = noteService.save(note);

        return noteMapper.toNoteResponse(savedNote);
    }

    @Transactional
    public NoteResponseDto update(UpdateNoteDto noteRequest) {
        Note note = noteService.findById(noteRequest.getId());

        Note updatedNote = noteMapper.toNote(noteRequest, note);

        if (noteRequest.getTweetId() != null) {
            Tweet tweet = tweetService.findById(noteRequest.getTweetId());
            updatedNote.setTweet(tweet);
        }

        return noteMapper.toNoteResponse(noteService.update(updatedNote));
    }

    @Transactional
    public void delete(Long id) {
        noteService.deleteById(id);
    }
}
