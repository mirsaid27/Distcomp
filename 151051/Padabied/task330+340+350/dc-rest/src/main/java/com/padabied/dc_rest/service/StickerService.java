package com.padabied.dc_rest.service;

import com.padabied.dc_rest.mapper.StickerMapper;
import com.padabied.dc_rest.model.Sticker;
import com.padabied.dc_rest.model.dto.requests.StickerRequestTo;
import com.padabied.dc_rest.model.dto.responses.StickerResponseTo;
import com.padabied.dc_rest.repository.interfaces.StickerRepository;
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
public class StickerService {

    private final StickerRepository stickerRepository;
    private final StickerMapper stickerMapper;

    @CacheEvict(value = "stickers", allEntries = true)
    public StickerResponseTo createSticker(StickerRequestTo stickerRequestDto) {
        Sticker sticker = stickerMapper.toEntity(stickerRequestDto);
        sticker = stickerRepository.save(sticker);
        return stickerMapper.toResponse(sticker);
    }

    @Cacheable(value = "stickers", key = "#id")
    public StickerResponseTo getStickerById(Long id) {
        Optional<Sticker> stickerOptional = stickerRepository.findById(id);
        return stickerOptional.map(stickerMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Sticker not found"));
    }

    @Cacheable(value = "stickersList")
    public List<StickerResponseTo> getAllStickers() {
        return stickerRepository.findAll().stream()
                .map(stickerMapper::toResponse)
                .toList();
    }
    @CacheEvict(value = "stickers", key = "#id")
    public StickerResponseTo updateSticker(Long id, StickerRequestTo stickerRequestDto) {
        Sticker existingSticker = stickerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sticker not found"));

        existingSticker.setName(stickerRequestDto.getName());

        stickerRepository.save(existingSticker);
        return stickerMapper.toResponse(existingSticker);
    }

    @CacheEvict(value = "stickers", key = "#id")
    public void deleteSticker(Long id) {
        if (!stickerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sticker not found");
        }
        stickerRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return stickerRepository.existsById(id);
    }

}
