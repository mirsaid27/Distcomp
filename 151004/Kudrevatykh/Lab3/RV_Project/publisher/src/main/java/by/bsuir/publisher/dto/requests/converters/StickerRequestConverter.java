package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Sticker;
import by.bsuir.publisher.dto.requests.StickerRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerRequestConverter {
    Sticker fromDto(StickerRequestDto sticker);
}
