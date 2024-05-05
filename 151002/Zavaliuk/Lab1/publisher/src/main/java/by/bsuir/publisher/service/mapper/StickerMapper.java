package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Sticker;
import by.bsuir.publisher.model.request.StickerRequestTo;
import by.bsuir.publisher.model.response.StickerResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    StickerResponseTo getResponseTo(Sticker sticker);

    List<StickerResponseTo> getListResponseTo(Iterable<Sticker> stickers);

    Sticker getSticker(StickerRequestTo stickerRequestTo);
}
