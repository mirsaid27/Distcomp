package by.harlap.jpa.controller;

import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.facade.NoteFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/notes")
public class NoteController {

    private final NoteFacade noteFacade;

    @GetMapping("/{id}")
    public NoteResponseDto findNoteById(@PathVariable("id") Long id) {
        return noteFacade.findById(id);
    }

    @GetMapping
    public List<NoteResponseDto> findAll() {
        return noteFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public NoteResponseDto saveNote(@RequestBody @Valid CreateNoteDto noteRequest) {
        return noteFacade.save(noteRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Long id) {
        noteFacade.delete(id);
    }

    @PutMapping
    public NoteResponseDto updateNote(@RequestBody @Valid UpdateNoteDto noteRequest) {
        return noteFacade.update(noteRequest);
    }
}

