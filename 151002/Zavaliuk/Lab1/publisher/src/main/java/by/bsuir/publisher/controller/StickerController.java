package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.request.StickerRequestTo;
import by.bsuir.publisher.model.response.StickerResponseTo;
import by.bsuir.publisher.service.StickerService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
@Data
@RequiredArgsConstructor
public class StickerController {
    private final StickerService stickerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StickerResponseTo> findAll() {
        return stickerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo create(@Valid @RequestBody StickerRequestTo dto) {
        return stickerService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo get(@Valid @PathVariable("id") Long id) {
        return stickerService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo update(@Valid @RequestBody StickerRequestTo dto) {
        return stickerService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        stickerService.removeById(id);
    }

}
