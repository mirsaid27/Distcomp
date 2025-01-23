package by.bsuir.publisherservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import by.bsuir.publisherservice.dto.request.StickerRequestTo;
import by.bsuir.publisherservice.dto.response.StickerResponseTo;
import by.bsuir.publisherservice.exception.EntityNotFoundException;
import by.bsuir.publisherservice.exception.EntityNotSavedException;
import by.bsuir.publisherservice.mapper.StickerMapper;
import by.bsuir.publisherservice.repository.StickerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "sticker")
public class StickerService {

    private final StickerMapper STICKER_MAPPER;
    private final StickerRepository STICKER_REPOSITORY;

    public List<StickerResponseTo> getAll(Pageable restriction) {
        return STICKER_REPOSITORY.findAll(restriction)
                             .stream()
                             .map(STICKER_MAPPER::toResponseTo)
                             .toList();
    }

    @Cacheable(key = "#id")
    public StickerResponseTo getById(Long id) {
        return STICKER_REPOSITORY.findById(id)
                             .map(STICKER_MAPPER::toResponseTo)
                             .orElseThrow(() -> 
                                 new EntityNotFoundException("Sticker", id));
    }

    public StickerResponseTo save(StickerRequestTo sticker) {
        return Optional.of(sticker)
                       .map(STICKER_MAPPER::toEntity)
                       .map(STICKER_REPOSITORY::save)
                       .map(STICKER_MAPPER::toResponseTo)
                       .orElseThrow(() -> 
                           new EntityNotSavedException("Sticker", sticker.id()));
    }

    @CachePut(key = "#sticker.id")
    public StickerResponseTo update(StickerRequestTo sticker) {
        return STICKER_REPOSITORY.findById(sticker.id())
                             .map(entityToUpdate -> STICKER_MAPPER.updateEntity(entityToUpdate, sticker))
                             .map(STICKER_REPOSITORY::save)
                             .map(STICKER_MAPPER::toResponseTo)
                             .orElseThrow(() -> 
                                 new EntityNotFoundException("Sticker", sticker.id()));
    }

    @CacheEvict(key = "#id", beforeInvocation = true)
    public void delete(Long id) {
        STICKER_REPOSITORY.findById(id)
                      .ifPresentOrElse(STICKER_REPOSITORY::delete,
                                       () -> { 
                                           throw new EntityNotFoundException("Sticker", id); 
                                       });  
        
    }
}
