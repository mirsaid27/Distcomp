package ru.bsuir.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.exceptions.IllegalFieldException;
import ru.bsuir.services.EditorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @PostMapping
    public ResponseEntity<EditorResponseTo> create(@RequestBody @Valid EditorRequestTo dto) {

        EditorResponseTo response = editorService.createEditor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EditorResponseTo>> getAll() {
        return new ResponseEntity<>(editorService.getAllEditors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorResponseTo> getById(@PathVariable Long id) {
        return editorService.getEditorById(id)
                .map(editor -> new ResponseEntity<>(editor, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<EditorResponseTo> update( @RequestBody @Valid EditorRequestTo dto) {
        if (dto.id() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return editorService.updateEditor(dto.id(), dto)
                .map(updatedEditor -> new ResponseEntity<>(updatedEditor, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EditorResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid EditorRequestTo dto) {
        return editorService.updateEditor(id, dto)
                .map(updatedEditor -> new ResponseEntity<>(updatedEditor, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (editorService.deleteEditor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
