package by.bsuir.discussion.controllers;

import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.responses.NoteResponseDto;
import by.bsuir.discussion.exceptions.EntityExistsException;
import by.bsuir.discussion.exceptions.Messages;
import by.bsuir.discussion.exceptions.NoEntityExistsException;
import by.bsuir.discussion.services.NoteService;
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
    private final NoteService messageService;

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@RequestBody NoteRequestDto message) throws EntityExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> read(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.read(id).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.readAll());
    }

    @PutMapping
    public ResponseEntity<NoteResponseDto> update(@RequestBody NoteRequestDto message) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.update(message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(messageService.delete(id));
    }
}
