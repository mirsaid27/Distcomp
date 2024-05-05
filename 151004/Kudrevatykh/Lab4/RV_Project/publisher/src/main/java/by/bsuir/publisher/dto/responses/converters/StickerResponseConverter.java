package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Sticker;
import by.bsuir.publisher.dto.responses.StickerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerResponseConverter {
    StickerResponseDto toDto(Sticker sticker);
}
