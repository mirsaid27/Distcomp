package by.bsuir.dc.impl.note;
import by.bsuir.dc.api.RestService;
import by.bsuir.dc.impl.note.model.Note;
import by.bsuir.dc.impl.note.model.NoteRequest;
import by.bsuir.dc.impl.note.model.NoteResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@AllArgsConstructor
public class NoteService implements RestService<NoteRequest, NoteResponse> {
    private NoteMapper noteMapper;
    private NoteRepository noteCrudRepository;

    @Override
    public List<NoteResponse> findAll() {
        Iterable<Note> Notes = noteCrudRepository.getAll();
        return noteMapper.toNoteResponseDto(Notes);
    }
    @Override
    public NoteResponse findById(long id) {
        Note note = noteCrudRepository.getBy(id).orElseThrow();
        return noteMapper.toNoteResponseDto(note);
    }
    @Override
    public NoteResponse create(NoteRequest request) {
        return noteCrudRepository
                .create(noteMapper.toNoteDto(request))
                .map(noteMapper::toNoteResponseDto)
                .orElseThrow();
    }
    @Override
    public NoteResponse update(NoteRequest request) {
        return noteCrudRepository
                .update(noteMapper.toNoteDto(request))
                .map(noteMapper::toNoteResponseDto)
                .orElseThrow();
    }
    @Override
    public boolean removeById(long id) {
        noteCrudRepository.deleteById(id);
        return true;
    }
}
