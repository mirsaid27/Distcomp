package by.bsuir.publisher.controllers;

import by.bsuir.publisher.dto.requests.NoteRequestDto;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.exceptions.ServiceException;
import by.bsuir.publisher.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@RequestBody NoteRequestDto note) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(note));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> read(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.read(id).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.readAll());
    }

    @PutMapping
    public ResponseEntity<NoteResponseDto> update(@RequestBody NoteRequestDto note) throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.update(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(noteService.delete(id));
    }
}
