package com.example.publicator.controller;

import com.example.publicator.dto.MarkerRequestTo;
import com.example.publicator.dto.MarkerResponseTo;
import com.example.publicator.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1.0/")
public class MarkerController {
    @Autowired
    MarkerService markerService;

    @PostMapping(value = "markers")
    public ResponseEntity<?> create(@RequestBody MarkerRequestTo markerRequestTo) {
        MarkerResponseTo marker = markerService.create(markerRequestTo);
        return new ResponseEntity<>(marker, HttpStatus.CREATED);
    }

    @GetMapping(value = "markers", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<MarkerResponseTo> list = markerService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "markers/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        MarkerResponseTo marker = markerService.read(id);
        return new ResponseEntity<>(marker, HttpStatus.OK);
    }

    @PutMapping(value = "markers")
    public ResponseEntity<?> update(@RequestBody MarkerRequestTo markerRequestTo) {
        MarkerResponseTo marker = markerService.update(markerRequestTo, markerRequestTo.getId());
        return new ResponseEntity<>(marker, HttpStatus.OK);
    }

    @DeleteMapping(value = "markers/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = markerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
