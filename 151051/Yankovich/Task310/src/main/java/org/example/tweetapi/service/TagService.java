package org.example.tweetapi.service;

import lombok.AllArgsConstructor;
import org.example.tweetapi.mapper.TagMapper;
import org.example.tweetapi.model.dto.request.TagRequestTo;
import org.example.tweetapi.model.dto.response.TagResponseTo;
import org.example.tweetapi.model.entity.Tag;
import org.example.tweetapi.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    // Создать новый стикер
    public TagResponseTo createTag(TagRequestTo tagRequestDto) {
        Tag tag = tagMapper.toEntity(tagRequestDto);
        tag = tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    // Получить стикер по id
    public TagResponseTo getTagById(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        return tagOptional.map(tagMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    // Получить все стикеры
    public List<TagResponseTo> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    // Обновить стикер по id
    public TagResponseTo updateTag(Long id, TagRequestTo tagRequestDto) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        existingTag.setName(tagRequestDto.getName());

        tagRepository.save(existingTag);
        return tagMapper.toResponse(existingTag);
    }

    // Удалить стикер по id
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
        tagRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return tagRepository.existsById(id);
    }

}
