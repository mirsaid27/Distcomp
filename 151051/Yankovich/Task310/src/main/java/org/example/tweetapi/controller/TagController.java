package org.example.tweetapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tweetapi.model.dto.request.TagRequestTo;
import org.example.tweetapi.model.dto.response.TagResponseTo;
import org.example.tweetapi.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // Создать новый стикер
    @PostMapping
    public ResponseEntity<TagResponseTo> createTag(@RequestBody @Valid TagRequestTo tagRequestDto) {
        TagResponseTo createdTag = tagService.createTag(tagRequestDto);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    // Получить все стикеры
    @GetMapping
    public ResponseEntity<List<TagResponseTo>> getAllTags() {
        List<TagResponseTo> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    // Получить стикер по id
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTo> getTagById(@PathVariable Long id) {
        TagResponseTo tag = tagService.getTagById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    // Обновить стикер по id
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

        // Используем ID из тела запроса для обновления
        TagResponseTo updatedTagResponseDto = tagService.updateTag(tagRequestDto.getId(), tagRequestDto);
        return new ResponseEntity<>(updatedTagResponseDto, HttpStatus.OK);
    }


    // Удалить стикер по id
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