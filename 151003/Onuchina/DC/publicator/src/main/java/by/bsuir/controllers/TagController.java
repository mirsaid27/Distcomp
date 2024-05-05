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
    TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponseTo>> getTags(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(tagService.getTags(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTo> getTag(@PathVariable Long id) {
        return ResponseEntity.status(200).body(tagService.getTagById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TagResponseTo> saveTag(@RequestBody TagRequestTo tag) {
        TagResponseTo savedTag = tagService.saveTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
    }

    @PutMapping()
    public ResponseEntity<TagResponseTo> updateTag(@RequestBody TagRequestTo tag) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.updateTag(tag));
    }

    @GetMapping("/byStory/{id}")
    public ResponseEntity<List<TagResponseTo>> getAuthorByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(tagService.getTagByStoryId(id));
    }
}