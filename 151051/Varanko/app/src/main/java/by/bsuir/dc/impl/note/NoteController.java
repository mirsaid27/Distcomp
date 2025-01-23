package by.bsuir.dc.impl.note;

import by.bsuir.dc.impl.note.model.NoteRequest;
import by.bsuir.dc.impl.note.model.NoteResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
@Data
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> findAll() {
        return noteService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponse findById(@PathVariable("id") long id) {
        return noteService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@RequestBody @Valid NoteRequest request) {
        return noteService.create(request);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NoteResponse update(@RequestBody @Valid NoteRequest request) {
        return noteService.update(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) { noteService.removeById(id); }
}
