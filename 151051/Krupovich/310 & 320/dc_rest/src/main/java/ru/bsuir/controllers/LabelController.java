package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.request.LabelRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.dto.response.LabelResponseTo;
import ru.bsuir.services.LabelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

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
        return labelService.getLabelById(id)
                .map(label -> new ResponseEntity<>(label, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<LabelResponseTo> update(@RequestBody @Valid LabelRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return labelService.updateLabel(dto.id(), dto)
                .map(updatedEditor -> new ResponseEntity<>(updatedEditor, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid LabelRequestTo dto) {
        return labelService.updateLabel(id, dto)
                .map(updatedLabel -> new ResponseEntity<>(updatedLabel, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (labelService.deleteLabel(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
