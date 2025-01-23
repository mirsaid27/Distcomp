package com.yankovich.dc_rest.controller;

import com.yankovich.dc_rest.model.dto.requests.TagRequestTo;
import com.yankovich.dc_rest.model.dto.responses.TagResponseTo;
import com.yankovich.dc_rest.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponseTo> createTag(@RequestBody @Valid TagRequestTo tagRequestDto) {
        TagResponseTo createdTag = tagService.createTag(tagRequestDto);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagResponseTo>> getAllTags() {
        List<TagResponseTo> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTo> getTagById(@PathVariable Long id) {
        TagResponseTo tag = tagService.getTagById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseTo> updateTag(@PathVariable Long id, @RequestBody @Valid TagRequestTo tagRequestDto) {
        TagResponseTo updatedTag = tagService.updateTag(id, tagRequestDto);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TagResponseTo> updateStory(@RequestBody @Valid TagRequestTo tagRequestDto) {
        if (tagRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TagResponseTo updatedTagResponseDto = tagService.updateTag(tagRequestDto.getId(), tagRequestDto);
        return new ResponseEntity<>(updatedTagResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try{
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
