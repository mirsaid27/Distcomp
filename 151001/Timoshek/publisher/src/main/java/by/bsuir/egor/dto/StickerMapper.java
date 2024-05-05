package by.bsuir.egor.dto;

import by.bsuir.egor.Entity.Sticker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface StickerMapper {
    StickerMapper INSTANCE = Mappers.getMapper( StickerMapper.class );

    Sticker stickerResponseToToSticker(StickerResponseTo stickerResponseTo);
    Sticker stickerRequestToToSticker(StickerRequestTo stickerRequestTo);

    StickerResponseTo stickerToStickerResponseTo(Sticker sticker);

    StickerRequestTo stickerToStickerRequestTo(Sticker sticker);
}
