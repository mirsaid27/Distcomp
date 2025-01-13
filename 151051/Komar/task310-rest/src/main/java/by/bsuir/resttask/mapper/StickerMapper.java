package by.bsuir.resttask.mapper;

import by.bsuir.resttask.dto.request.StickerRequestTo;
import by.bsuir.resttask.dto.response.StickerResponseTo;
import by.bsuir.resttask.entity.Sticker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    Sticker toEntity(StickerRequestTo request);

    StickerResponseTo toResponseTo(Sticker entity);

    Sticker updateEntity(@MappingTarget Sticker entityToUpdate, StickerRequestTo updateRequest);
}
