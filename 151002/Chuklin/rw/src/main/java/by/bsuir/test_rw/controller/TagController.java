package by.bsuir.test_rw.controller;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.dto.tag.TagRequestTO;
import by.bsuir.test_rw.model.dto.tag.TagResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Tag;
import by.bsuir.test_rw.repository.implementations.in_memory.TagInMemoryRepo;
import by.bsuir.test_rw.service.db_interaction.interfaces.TagService;
import by.bsuir.test_rw.service.dto_convert.interfaces.TagToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagToConverter converter;

    @PostMapping()
    public ResponseEntity<TagResponseTO> createTag(@RequestBody @Valid TagRequestTO tagRequestTO) {
        Tag tag = converter.convertToEntity(tagRequestTO);
        tagService.save(tag);
        TagResponseTO stickerResponseTo = converter.convertToDto(tag);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stickerResponseTo);
    }

    @GetMapping()
    public ResponseEntity<List<TagResponseTO>> receiveAllTags() {
        List<Tag> tags = tagService.findAll();
        List<TagResponseTO> responseList = tags.stream()
                .map(converter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTO> receiveTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        TagResponseTO stickerResponseTo = converter.convertToDto(tag);
        return ResponseEntity.ok(stickerResponseTo);
    }

    @PutMapping()
    public ResponseEntity<TagResponseTO> updateTag(@RequestBody @Valid TagRequestTO tagRequestTO) {
        Tag tag = converter.convertToEntity(tagRequestTO);
        tagService.save(tag);
        TagResponseTO stickerResponseTo = converter.convertToDto(tag);
        return ResponseEntity.ok(stickerResponseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id) {
        try {
            tagService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
