package com.example.controllers;

import com.example.dto.StickerRequestTo;
import com.example.dto.StickerResponseTo;
import com.example.services.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
public class StickerController {
    @Autowired
    private StickerService stickerService;

    @GetMapping
    public ResponseEntity<List<StickerResponseTo>> getStickers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getStickers(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> getSticker(@PathVariable BigInteger id) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getStickerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable BigInteger id) {
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

