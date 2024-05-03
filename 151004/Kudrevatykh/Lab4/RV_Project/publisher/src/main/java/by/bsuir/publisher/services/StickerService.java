package by.bsuir.publisher.services;

import by.bsuir.publisher.dto.requests.StickerRequestDto;
import by.bsuir.publisher.dto.responses.StickerResponseDto;

import java.util.List;

public interface StickerService extends BaseService<StickerRequestDto, StickerResponseDto> {
    List<StickerResponseDto> readAll();
}
