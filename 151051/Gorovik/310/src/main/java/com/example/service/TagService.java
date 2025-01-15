package com.example.service;

import com.example.repository.TagRepository;
import com.example.request.TagRequestTo;
import com.example.response.TagResponseTo;
import com.example.exceptions.ResourceStateException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.TagMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class TagService implements IService<TagRequestTo, TagResponseTo> {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponseTo findById(Long id) {
        return tagRepository.getById(id).map(tagMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<TagResponseTo> findAll() {
        return tagMapper.getListResponse(tagRepository.getAll());
    }

    @Override
    public TagResponseTo create(TagRequestTo request) {
        return tagRepository.save(tagMapper.getTag(request)).map(tagMapper::getResponse).orElseThrow(TagService::createException);
    }

    @Override
    public TagResponseTo update(TagRequestTo request) {
        if (tagMapper.getTag(request).getId() == null)
        {
            throw findByIdException(tagMapper.getTag(request).getId());
        }

        return tagRepository.update(tagMapper.getTag(request)).map(tagMapper::getResponse).orElseThrow(TagService::updateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!tagRepository.removeById(id)) {
            throw removeException();
        }
        return true;
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 21, "Can't find tag by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 22, "Can't create tag");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 23, "Can't update tag");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 24, "Can't remove tag");
    }
}