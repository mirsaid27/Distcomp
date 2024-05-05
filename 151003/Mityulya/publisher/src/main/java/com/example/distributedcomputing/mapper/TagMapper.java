package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Sticker;
import com.example.distributedcomputing.model.request.StickerRequestTo;
import com.example.distributedcomputing.model.response.StickerResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Sticker dtoToEntity(StickerRequestTo stickerRequestTo);
    List<Sticker> dtoToEntity(Iterable<Sticker> tags);

    StickerResponseTo entityToDto(Sticker sticker);

    List<StickerResponseTo> entityToDto(Iterable<Sticker> tags);
}
