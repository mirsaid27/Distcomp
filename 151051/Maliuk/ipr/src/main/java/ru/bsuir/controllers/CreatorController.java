package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.CreatorRequestTo;
import ru.bsuir.dto.response.CreatorResponseTo;
import ru.bsuir.services.CreatorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping
    public ResponseEntity<CreatorResponseTo> create(@RequestBody @Valid CreatorRequestTo dto) {
        CreatorResponseTo response = creatorService.createCreator(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getAll() {
        List<CreatorResponseTo> response = creatorService.getAllCreator();
        return new ResponseEntity<>(response, HttpStatus.OK);    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> getById(@PathVariable Long id) {
        CreatorResponseTo user = creatorService.getCreatorById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CreatorResponseTo> update(@RequestBody @Valid CreatorRequestTo creatorRequestTo) {
        if (creatorRequestTo.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CreatorResponseTo response = creatorService.updateCreator(creatorRequestTo.getId(), creatorRequestTo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid CreatorRequestTo creatorRequest) {
        CreatorResponseTo response = creatorService.updateCreator(id, creatorRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!creatorService.existById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        creatorService.deleteCreator(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
