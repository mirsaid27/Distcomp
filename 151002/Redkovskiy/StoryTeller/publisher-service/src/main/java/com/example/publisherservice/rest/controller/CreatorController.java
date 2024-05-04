package com.example.publisherservice.rest.controller;

import com.example.publisherservice.dto.requestDto.CreatorRequestTo;
import com.example.publisherservice.dto.responseDto.CreatorResponseTo;
import com.example.publisherservice.service.CreatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/creators")
public class CreatorController {

    private final CreatorService creatorService;

    @Autowired
    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping("")
    public Iterable<CreatorResponseTo> getTasks() {
        return creatorService.findAllDtos();
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo getTask(@PathVariable final Long id) {
        return creatorService.findDtoById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatorResponseTo addTask(@Valid @RequestBody CreatorRequestTo taskDto) {
        return creatorService.create(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo updateTask(@Valid @RequestBody final CreatorRequestTo taskDto) {
        return creatorService.update(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable final Long id) {
        creatorService.delete(id);
    }
}
