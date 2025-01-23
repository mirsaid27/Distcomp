package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.LabelRequestTo;
import ru.bsuir.dto.response.LabelResponseTo;
import ru.bsuir.services.LabelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<LabelResponseTo> create(@RequestBody @Valid LabelRequestTo dto) {
        LabelResponseTo response = labelService.createLabel(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LabelResponseTo>> getAll() {
        return new ResponseEntity<>(labelService.getAllLabels(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseTo> getById(@PathVariable Long id) {
        LabelResponseTo sticker = labelService.getLabelById(id);
        return new ResponseEntity<>(sticker, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<LabelResponseTo> update(@RequestBody @Valid LabelRequestTo labelRequest) {
        if (labelRequest.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LabelResponseTo response = labelService.updateLabel(labelRequest.getId(), labelRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid LabelRequestTo labelRequestTo) {
        LabelResponseTo updatedSticker = labelService.updateLabel(id, labelRequestTo);
        return new ResponseEntity<>(updatedSticker, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try{
            labelService.deleteLabel(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
