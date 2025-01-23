package com.rmakovetskij.dc_rest.controller;

import com.rmakovetskij.dc_rest.model.dto.requests.EditorRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.EditorResponseTo;
import com.rmakovetskij.dc_rest.service.EditorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
@RequiredArgsConstructor
public class EditorController {

    private final EditorService editorService;

    @PostMapping
    public ResponseEntity<EditorResponseTo> createEditor(@RequestBody @Valid EditorRequestTo editorRequestDto) {
            EditorResponseTo createdEditor = editorService.createEditor(editorRequestDto);
            return new ResponseEntity<>(createdEditor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorResponseTo> getEditorById(@PathVariable Long id) {
        EditorResponseTo editor = editorService.getEditorById(id);
        return new ResponseEntity<>(editor, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EditorResponseTo>> getAllEditors() {
        List<EditorResponseTo> editorsResponseDtos = editorService.getAllEditors();
        return new ResponseEntity<>(editorsResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditorResponseTo> updateEditor(@PathVariable Long id, @RequestBody @Valid EditorRequestTo editorRequestDto) {
        EditorResponseTo updatedEditor = editorService.updateEditor(id, editorRequestDto);
        return new ResponseEntity<>(updatedEditor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EditorResponseTo> updateEditor(@RequestBody @Valid EditorRequestTo editorRequestDto) {
        if (editorRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EditorResponseTo updatedEditor = editorService.updateEditor(editorRequestDto.getId(), editorRequestDto);
        return new ResponseEntity<>(updatedEditor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable Long id) {
        if (!editorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        editorService.deleteEditor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
