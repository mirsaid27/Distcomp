package com.example.mapper;

import com.example.dto.StickerRequestTo;
import com.example.dto.StickerResponseTo;
import com.example.entities.Sticker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker stickerRequestToSticker(StickerRequestTo stickerRequestTo);
    StickerResponseTo stickerToStickerResponse(Sticker sticker);
}


