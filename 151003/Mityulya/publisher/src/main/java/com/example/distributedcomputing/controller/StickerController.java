package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.StickerRequestTo;
import com.example.distributedcomputing.model.response.StickerResponseTo;
import com.example.distributedcomputing.service.StickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/stickers")
@RequiredArgsConstructor
public class StickerController {
    private final StickerService stickerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo create(@RequestBody StickerRequestTo stickerRequestTo) {
        return stickerService.save(stickerRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        stickerService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<StickerResponseTo> getAll() {
        return stickerService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo getById(@PathVariable Long id) {
        return stickerService.getById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo update(@RequestBody StickerRequestTo stickerRequestTo) {
        return stickerService.update(stickerRequestTo);
    }
}
