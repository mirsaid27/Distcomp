package com.example.mapper;

import com.example.dto.StickerRequestTo;
import com.example.dto.StickerResponseTo;
import com.example.entities.Sticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StickerMapper.class)
public interface StickerListMapper {
    List<Sticker> toStickerList(List<StickerRequestTo> stickers);
    List<StickerResponseTo> toStickerResponseList(List<Sticker> stickers);
}

