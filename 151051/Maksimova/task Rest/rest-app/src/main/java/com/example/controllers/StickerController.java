package com.example.controllers;

import com.example.api.dto.StickerRequestTo;
import com.example.api.dto.StickerResponseTo;
import com.example.services.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
public class StickerController {
    @Autowired
    StickerService stickerService;

    @GetMapping
    public ResponseEntity<List<StickerResponseTo>> getStickers() {
        return ResponseEntity.status(200).body(stickerService.getStickers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> getSticker(@PathVariable Long id) {
        return ResponseEntity.status(200).body(stickerService.getStickerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable Long id) {
        stickerService.deleteSticker(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<StickerResponseTo> saveSticker(@RequestBody StickerRequestTo sticker) {
        StickerResponseTo savedSticker = stickerService.saveSticker(sticker);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSticker);
    }

    @PutMapping
    public ResponseEntity<StickerResponseTo> updateSticker(@RequestBody StickerRequestTo sticker) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.updateSticker(sticker));
    }
}
