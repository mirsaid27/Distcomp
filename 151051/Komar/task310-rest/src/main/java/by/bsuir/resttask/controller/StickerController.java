package by.bsuir.resttask.controller;

import by.bsuir.resttask.dto.request.StickerRequestTo;
import by.bsuir.resttask.dto.response.StickerResponseTo;
import by.bsuir.resttask.service.StickerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stickers")
public class StickerController {

    private final StickerService STICKER_SERVICE;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo getById(@PathVariable Long id) {
        return STICKER_SERVICE.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StickerResponseTo> getAll() {
        return STICKER_SERVICE.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo save(@RequestBody @Valid StickerRequestTo sticker) {
        return STICKER_SERVICE.save(sticker);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo update(@RequestBody @Valid StickerRequestTo sticker) {
        return STICKER_SERVICE.update(sticker);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        STICKER_SERVICE.delete(id);
    }
}
