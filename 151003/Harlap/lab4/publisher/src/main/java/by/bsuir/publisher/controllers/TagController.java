package by.bsuir.publisher.controllers;

import by.bsuir.publisher.dto.requests.TagRequestDto;
import by.bsuir.publisher.dto.responses.TagResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Messages;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponseDto> create(@RequestBody TagRequestDto tag) throws EntityExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> read(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.read(id).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.readAll());
    }

    @PutMapping
    public ResponseEntity<TagResponseDto> update(@RequestBody TagRequestDto tag) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.update(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tagService.delete(id));
    }
}
