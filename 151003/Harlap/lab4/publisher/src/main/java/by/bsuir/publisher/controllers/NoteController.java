package by.bsuir.publisher.controllers;

import by.bsuir.publisher.dto.requests.NoteRequestDto;
import by.bsuir.publisher.dto.responses.NoteResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.exceptions.ServiceException;
import by.bsuir.publisher.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@RequestBody NoteRequestDto note) throws EntityExistsException, ServiceException {
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
    public ResponseEntity<NoteResponseDto> update(@RequestBody NoteRequestDto message) throws NoEntityExistsException, ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.update(message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws NoEntityExistsException, ServiceException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(noteService.delete(id));
    }
}
