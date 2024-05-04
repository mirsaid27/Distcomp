package by.harlap.jpa.controller;

import by.harlap.jpa.dto.request.CreateEditorDto;
import by.harlap.jpa.dto.request.UpdateEditorDto;
import by.harlap.jpa.dto.response.EditorResponseDto;
import by.harlap.jpa.facade.EditorFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/editors")
public class EditorController {

    private final EditorFacade editorFacade;

    @GetMapping("/{id}")
    public EditorResponseDto findEditorById(@PathVariable("id") Long id) {
        return editorFacade.findById(id);
    }

    @GetMapping
    public List<EditorResponseDto> findAll() {
        return editorFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EditorResponseDto saveEditor(@RequestBody @Valid CreateEditorDto editorRequest) {
        return editorFacade.save(editorRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEditor(@PathVariable("id") Long id) {
        editorFacade.delete(id);
    }

    @PutMapping
    public EditorResponseDto updateEditor(@RequestBody @Valid UpdateEditorDto editorRequest) {
        return editorFacade.update(editorRequest);
    }
}
