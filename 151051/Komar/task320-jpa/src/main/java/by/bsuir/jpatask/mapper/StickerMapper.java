package by.bsuir.jpatask.mapper;

import by.bsuir.jpatask.dto.request.StickerRequestTo;
import by.bsuir.jpatask.dto.response.StickerResponseTo;
import by.bsuir.jpatask.entity.Sticker;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker toEntity(StickerRequestTo request);

    StickerResponseTo toResponseTo(Sticker entity);

    Sticker updateEntity(@MappingTarget Sticker entityToUpdate, StickerRequestTo updateRequest);
}
