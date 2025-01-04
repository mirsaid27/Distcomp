package com.example.mapper;

import com.example.api.dto.StickerRequestTo;
import com.example.api.dto.StickerResponseTo;
import com.example.entities.Sticker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker StickerRequestToSticker(StickerRequestTo stickerRequestTo);

    StickerResponseTo StickerToStickerResponse(Sticker sticker);
}
