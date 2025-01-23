package by.bsuir.publisherservice.mapper;

import by.bsuir.publisherservice.dto.request.StickerRequestTo;
import by.bsuir.publisherservice.dto.response.StickerResponseTo;
import by.bsuir.publisherservice.entity.Sticker;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    @Mapping(target = "id", ignore = true)
    Sticker toEntity(StickerRequestTo request);

    StickerResponseTo toResponseTo(Sticker entity);
    
    @Mapping(target = "id", ignore = true)
    Sticker updateEntity(@MappingTarget Sticker entityToUpdate, StickerRequestTo updateRequest);
}
