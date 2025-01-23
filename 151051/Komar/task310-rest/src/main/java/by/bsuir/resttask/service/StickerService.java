package by.bsuir.resttask.service;

import by.bsuir.resttask.dto.request.StickerRequestTo;
import by.bsuir.resttask.dto.response.StickerResponseTo;
import by.bsuir.resttask.exception.EntityNotFoundException;
import by.bsuir.resttask.exception.EntityNotSavedException;
import by.bsuir.resttask.mapper.StickerMapper;
import by.bsuir.resttask.repository.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StickerService {

    private final StickerMapper STICKER_MAPPER;
    private final StickerRepository STICKER_REPOSITORY;

    public List<StickerResponseTo> getAll() {
        return STICKER_REPOSITORY.findAll()
                .stream()
                .map(STICKER_MAPPER::toResponseTo)
                .toList();
    }

    ;

    public StickerResponseTo getById(Long id) {
        return STICKER_REPOSITORY.findById(id)
                .map(STICKER_MAPPER::toResponseTo)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sticker", id));
    }

    ;

    public StickerResponseTo save(StickerRequestTo sticker) {
        return Optional.of(sticker)
                .map(STICKER_MAPPER::toEntity)
                .map(STICKER_REPOSITORY::save)
                .map(STICKER_MAPPER::toResponseTo)
                .orElseThrow(() ->
                        new EntityNotSavedException("Sticker", sticker.id()));
    }

    ;

    public StickerResponseTo update(StickerRequestTo sticker) {
        return STICKER_REPOSITORY.findById(sticker.id())
                .map(entityToUpdate -> STICKER_MAPPER.updateEntity(entityToUpdate, sticker))
                .map(STICKER_REPOSITORY::save)
                .map(STICKER_MAPPER::toResponseTo)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sticker", sticker.id()));
    }

    ;

    public void delete(Long id) {
        STICKER_REPOSITORY.findById(id)
                .ifPresentOrElse(STICKER_REPOSITORY::delete,
                        () -> {
                            throw new EntityNotFoundException("Sticker", id);
                        });

    }

    ;
}
