package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.TagMapper;
import com.example.distributedcomputing.model.entity.Sticker;
import com.example.distributedcomputing.model.request.StickerRequestTo;
import com.example.distributedcomputing.model.response.StickerResponseTo;
import com.example.distributedcomputing.repository.StickerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class StickerService {
    private final StickerRepository repository;
    private final TagMapper mapper;

    public Iterable<StickerResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public StickerResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public StickerResponseTo save(@Valid StickerRequestTo entity) {
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public StickerResponseTo update(@Valid StickerRequestTo entity) {
        Long id = entity.id();
        Sticker editor = mapper.dtoToEntity(entity);
        editor.setId(id);
        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
