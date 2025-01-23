package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.CreatorRequestTo;
import ru.bsuir.dto.response.CreatorResponseTo;
import ru.bsuir.services.CreatorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
public class CreatorController {

    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> create(@RequestBody @Valid CreatorRequestTo dto) {

        CreatorResponseTo response = editorService.createEditor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getAll() {
        return new ResponseEntity<>(creatorService.getAllCreators(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> getById(@PathVariable Long id) {
        return creatorService.getCreatorById(id)
                .map(creator -> new ResponseEntity<>(creator, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<CreatorResponseTo> update( @RequestBody @Valid CreatorRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return creatorService.updateCreator(dto.id(), dto)
                .map(updatedCreator -> new ResponseEntity<>(updatedCreator, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid CreatorRequestTo dto) {
        return creatorService.updateCreator(id, dto)
                .map(updatedCreator -> new ResponseEntity<>(updatedCreator, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (creatorService.deleteCreator(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
