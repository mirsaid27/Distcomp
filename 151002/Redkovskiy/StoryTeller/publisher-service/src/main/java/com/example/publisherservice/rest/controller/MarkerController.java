package com.example.publisherservice.rest.controller;

import com.example.publisherservice.dto.requestDto.MarkerRequestTo;
import com.example.publisherservice.dto.responseDto.MarkerResponseTo;
import com.example.publisherservice.service.MarkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/markers")
public class MarkerController {

    private final MarkerService markerService;

    @Autowired
    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping("")
    public Iterable<MarkerResponseTo> getTasks() {
        return markerService.findAllDtos();
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo getTask(@PathVariable final Long id) {
        return markerService.findDtoById(id);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo addTask(@Valid @RequestBody MarkerRequestTo taskDto) {
        return markerService.create(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo updateTask(@Valid @RequestBody final MarkerRequestTo taskDto) {
        return markerService.update(taskDto);
    }

    //@PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable final Long id) {
        markerService.delete(id);
    }
}
