package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Sticker;
import com.padabied.dc_rest.model.dto.requests.StickerRequestTo;
import com.padabied.dc_rest.model.dto.responses.StickerResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StickerMapper {

    StickerResponseTo toResponse(Sticker sticker);

    @Mapping(target = "id", ignore = true)
    Sticker toEntity(StickerRequestTo stickerRequestDto);
}
