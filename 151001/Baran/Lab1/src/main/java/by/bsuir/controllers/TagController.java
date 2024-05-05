package by.bsuir.controllers;

import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
public class TagController {
    @Autowired
    TagService TagService;

    @GetMapping
    public ResponseEntity<List<TagResponseTo>> getTags() {
        return ResponseEntity.status(200).body(TagService.getTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTo> getTag(@PathVariable Long id) {
        return ResponseEntity.status(200).body(TagService.getTagById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        TagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TagResponseTo> saveTag(@RequestBody TagRequestTo Tag) {
        TagResponseTo savedTag = TagService.saveTag(Tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
    }

    @PutMapping()
    public ResponseEntity<TagResponseTo> updateTag(@RequestBody TagRequestTo Tag) {
        return ResponseEntity.status(HttpStatus.OK).body(TagService.updateTag(Tag));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<TagResponseTo> getEditorByIssueId(@PathVariable Long id){
        return ResponseEntity.status(200).body(TagService.getTagByIssueId(id));
    }
}