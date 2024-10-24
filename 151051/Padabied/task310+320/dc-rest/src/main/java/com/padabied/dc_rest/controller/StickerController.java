package com.padabied.dc_rest.controller;

import com.padabied.dc_rest.model.dto.requests.StickerRequestTo;
import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StickerResponseTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import com.padabied.dc_rest.service.StickerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final StickerService stickerService;

    // Создать новый стикер
    @PostMapping
    public ResponseEntity<StickerResponseTo> createSticker(@RequestBody @Valid StickerRequestTo stickerRequestDto) {
        StickerResponseTo createdSticker = stickerService.createSticker(stickerRequestDto);
        return new ResponseEntity<>(createdSticker, HttpStatus.CREATED);
    }

    // Получить все стикеры
    @GetMapping
    public ResponseEntity<List<StickerResponseTo>> getAllStickers() {
        List<StickerResponseTo> stickers = stickerService.getAllStickers();
        return new ResponseEntity<>(stickers, HttpStatus.OK);
    }

    // Получить стикер по id
    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> getStickerById(@PathVariable Long id) {
        StickerResponseTo sticker = stickerService.getStickerById(id);
        return new ResponseEntity<>(sticker, HttpStatus.OK);
    }

    // Обновить стикер по id
    @PutMapping("/{id}")
    public ResponseEntity<StickerResponseTo> updateSticker(@PathVariable Long id, @RequestBody @Valid StickerRequestTo stickerRequestDto) {
        StickerResponseTo updatedSticker = stickerService.updateSticker(id, stickerRequestDto);
        return new ResponseEntity<>(updatedSticker, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StickerResponseTo> updateStory(@RequestBody @Valid StickerRequestTo stickerRequestDto) {
        if (stickerRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Используем ID из тела запроса для обновления
        StickerResponseTo updatedStickerResponseDto = stickerService.updateSticker(stickerRequestDto.getId(), stickerRequestDto);
        return new ResponseEntity<>(updatedStickerResponseDto, HttpStatus.OK);
    }


    // Удалить стикер по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable Long id) {
        try{
        stickerService.deleteSticker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
