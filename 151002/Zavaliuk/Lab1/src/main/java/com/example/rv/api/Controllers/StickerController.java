package com.example.rv.api.Controllers;

import com.example.rv.api.exception.DuplicateEntityException;
import com.example.rv.api.exception.EntityNotFoundException;
import com.example.rv.impl.sticker.Service.StickerService;
import com.example.rv.impl.sticker.dto.StickerRequestTo;
import com.example.rv.impl.sticker.dto.StickerResponseTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/stickers")
@RequiredArgsConstructor
public class StickerController {
    private final StickerService stickerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StickerResponseTo> getStickers() {
        return stickerService.getStickers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo getStickerById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return stickerService.getStickerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo saveSticker(@Valid @RequestBody StickerRequestTo stickerTo) throws DuplicateEntityException, EntityNotFoundException {
        return stickerService.saveSticker(stickerTo);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo updateSticker(@Valid @RequestBody StickerRequestTo stickerTo) throws DuplicateEntityException, EntityNotFoundException {
        return stickerService.updateSticker(stickerTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSticker(@PathVariable BigInteger id) throws EntityNotFoundException {
        stickerService.deleteStickerById(id);
    }

}
