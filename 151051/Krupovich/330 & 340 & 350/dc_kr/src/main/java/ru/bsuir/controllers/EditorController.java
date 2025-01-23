package ru.bsuir.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsuir.dto.request.EditorRequestTo;
import ru.bsuir.dto.response.EditorResponseTo;
import ru.bsuir.services.EditorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
@RequiredArgsConstructor
public class EditorController {

    private final EditorService editorService;

    @PostMapping
    public ResponseEntity<EditorResponseTo> create(@RequestBody @Valid EditorRequestTo dto) {
        EditorResponseTo response = editorService.createEditor(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EditorResponseTo>> getAll() {
        List<EditorResponseTo> response = editorService.getAllEditors();
        return new ResponseEntity<>(response, HttpStatus.OK);    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorResponseTo> getById(@PathVariable Long id) {
        EditorResponseTo user = editorService.getEditorById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EditorResponseTo> update( @RequestBody @Valid EditorRequestTo editorRequestTo) {
        if (editorRequestTo.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EditorResponseTo response = editorService.updateEditor(editorRequestTo.getId(), editorRequestTo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EditorResponseTo> updateById(@PathVariable Long id, @RequestBody @Valid EditorRequestTo editorRequest) {
        EditorResponseTo response = editorService.updateEditor(id, editorRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!editorService.existById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        editorService.deleteEditor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
