package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Sticker;
import by.bsuir.publisher.dto.responses.StickerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StickerResponseConverter.class)
public interface CollectionStickerResponseConverter {
    List<StickerResponseDto> toListDto(List<Sticker> stickers);
}
