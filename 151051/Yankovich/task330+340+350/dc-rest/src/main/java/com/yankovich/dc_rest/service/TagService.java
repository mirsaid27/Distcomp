package com.yankovich.dc_rest.service;

import com.yankovich.dc_rest.mapper.TagMapper;
import com.yankovich.dc_rest.model.Tag;
import com.yankovich.dc_rest.model.dto.requests.TagRequestTo;
import com.yankovich.dc_rest.model.dto.responses.TagResponseTo;
import com.yankovich.dc_rest.repository.interfaces.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @CacheEvict(value = "tags", allEntries = true)
    public TagResponseTo createTag(TagRequestTo tagRequestDto) {
        Tag tag = tagMapper.toEntity(tagRequestDto);
        tag = tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    @Cacheable(value = "tags", key = "#id")
    public TagResponseTo getTagById(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        return tagOptional.map(tagMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Cacheable(value = "tagsList")
    public List<TagResponseTo> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toResponse)
                .toList();
    }
    @CacheEvict(value = "tags", key = "#id")
    public TagResponseTo updateTag(Long id, TagRequestTo tagRequestDto) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        existingTag.setName(tagRequestDto.getName());

        tagRepository.save(existingTag);
        return tagMapper.toResponse(existingTag);
    }

    @CacheEvict(value = "tags", key = "#id")
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
