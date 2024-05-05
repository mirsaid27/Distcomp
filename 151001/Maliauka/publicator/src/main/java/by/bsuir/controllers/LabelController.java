package by.bsuir.controllers;

import by.bsuir.dto.CommentResponseTo;
import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
public class LabelController {
    @Autowired
    LabelService labelService;

    @GetMapping
    public ResponseEntity<List<LabelResponseTo>> getLabels(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(labelService.getLabels(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseTo> getLabel(@PathVariable Long id) {
        return ResponseEntity.status(200).body(labelService.getLabelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<LabelResponseTo> saveLabel(@RequestBody LabelRequestTo label) {
        LabelResponseTo savedLabel = labelService.saveLabel(label);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLabel);
    }

    @PutMapping()
    public ResponseEntity<LabelResponseTo> updateLabel(@RequestBody LabelRequestTo label) {
        return ResponseEntity.status(HttpStatus.OK).body(labelService.updateLabel(label));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<List<LabelResponseTo>> getEditorByIssueId(@PathVariable Long id){
        return ResponseEntity.status(200).body(labelService.getLabelByIssueId(id));
    }
}