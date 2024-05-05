package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.CreatorRequestTo;
import com.example.distributedcomputing.model.response.CreatorResponseTo;
import com.example.distributedcomputing.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/creators")
@RequiredArgsConstructor
public class CreatorController {
	private final CreatorService creatorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatorResponseTo create(@RequestBody CreatorRequestTo creatorRequestTo) {
        return creatorService.save(creatorRequestTo);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo update(@RequestBody CreatorRequestTo creatorRequestTo) {
        return creatorService.update(creatorRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
       creatorService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<CreatorResponseTo> getAll() {
        return creatorService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo getById(@PathVariable Long id) {
        return creatorService.getById(id);
    }
}
