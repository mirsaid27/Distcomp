package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateNoteDto;
import by.harlap.rest.dto.request.UpdateNoteDto;
import by.harlap.rest.dto.response.NoteResponseDto;
import by.harlap.rest.mapper.NoteMapper;
import by.harlap.rest.model.Note;
import by.harlap.rest.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteFacade {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteResponseDto findById(Long id) {
        Note note = noteService.findById(id);
        return noteMapper.toNoteResponse(note);
    }

    public List<NoteResponseDto> findAll() {
        List<Note> notes = noteService.findAll();

        return notes.stream().map(noteMapper::toNoteResponse).toList();
    }

    public NoteResponseDto save(CreateNoteDto noteRequest) {
        Note note = noteMapper.toNote(noteRequest);

        Note savedNote = noteService.save(note);

        return noteMapper.toNoteResponse(savedNote);
    }

    public NoteResponseDto update(UpdateNoteDto noteRequest) {
        Note note = noteService.findById(noteRequest.getId());

        Note updatedNote = noteMapper.toNote(noteRequest, note);

        return noteMapper.toNoteResponse(noteService.update(updatedNote));
    }

    public void delete(Long id) {
        noteService.deleteById(id);
    }
}
