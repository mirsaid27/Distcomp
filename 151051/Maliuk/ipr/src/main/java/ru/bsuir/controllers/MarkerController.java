package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.MarkerRequestTo;
import ru.bsuir.dto.response.MarkerResponseTo;
import ru.bsuir.services.MarkerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/markers")
@RequiredArgsConstructor
public class MarkerController {

    private final MarkerService markerService;

    @PostMapping
    public ResponseEntity<MarkerResponseTo> create(@RequestBody @Valid MarkerRequestTo dto) {
        MarkerResponseTo response = markerService.createMarker(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MarkerResponseTo>> getAll() {
        return new ResponseEntity<>(markerService.getAllMarker(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseTo> getById(@PathVariable Long id) {
        MarkerResponseTo marker = markerService.getMarkerById(id);
        return new ResponseEntity<>(marker, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<MarkerResponseTo> update(@RequestBody @Valid MarkerRequestTo labelRequest) {
        if (labelRequest.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MarkerResponseTo response = markerService.updateMarker(labelRequest.getId(), labelRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarkerResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid MarkerRequestTo markerRequestTo) {
        MarkerResponseTo updated = markerService.updateMarker(id, markerRequestTo);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try{
            markerService.deleteMarker(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
