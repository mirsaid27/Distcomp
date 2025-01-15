package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.MarkerRequestTo;
import ru.bsuir.dto.response.MarkerResponseTo;
import ru.bsuir.services.MarkerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
public class MarkerController {

    private final MarkerService markerService;

    public MarkerController(MarkerService markerService) {this.markerService = markerService;}

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
        return markerService.getMarkerById(id)
                .map(marker -> new ResponseEntity<>(marker, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<MarkerResponseTo> update(@RequestBody @Valid MarkerRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return markerService.updateMarker(dto.id(), dto)
                .map(updatedEditor -> new ResponseEntity<>(updatedEditor, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarkerResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid MarkerRequestTo dto) {
        return markerService.updateMarker(id, dto)
                .map(updatedMarker -> new ResponseEntity<>(updatedMarker, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (markerService.deleteMarker(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
