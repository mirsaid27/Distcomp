package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateStickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.services.StickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/stickers")
public class StickerController {
    private final StickerService stickerService;

    @PostMapping
    public ResponseEntity<StickerDto> createLabel(
	@RequestBody @Validated(Create.class) UpdateStickerDto dto
    ) {
	var response = stickerService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<StickerDto> getStickers() {
	return stickerService.getAll();
    }

    @GetMapping("/{stickerId}")
    public StickerDto getStickerById(
	@PathVariable long stickerId
    ) {
	return stickerService.getById(stickerId);
    }

    @PutMapping
    public StickerDto updateStickerById(
	@RequestBody @Validated(Update.class) UpdateStickerDto dto
    ) {
	long stickerId = dto.id();
	return stickerService.update(stickerId, dto);
    }

    @DeleteMapping("/{stickerId}")
    public Object deleteStickerById(
	@PathVariable long stickerId
    ) {
	return stickerService.delete(stickerId);
    }
}
