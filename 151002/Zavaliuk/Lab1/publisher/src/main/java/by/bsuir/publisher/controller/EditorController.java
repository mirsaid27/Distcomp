package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.request.EditorRequestTo;
import by.bsuir.publisher.model.response.EditorResponseTo;
import by.bsuir.publisher.service.EditorService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
@Data
@RequiredArgsConstructor
public class EditorController {
    private final EditorService editorService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EditorResponseTo> findAll() {
        return editorService.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo create(@Valid @RequestBody EditorRequestTo dto) {
        return editorService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo get(@Valid @PathVariable("id") Long id) {
        return editorService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo update(@Valid @RequestBody EditorRequestTo dto) {
        return editorService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        editorService.removeById(id);
    }
}
