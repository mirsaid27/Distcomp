package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.CreatorRequestTo;
import ru.bsuir.dto.response.CreatorResponseTo;
import ru.bsuir.entity.Creator;

@Mapper(componentModel = "spring")
public interface CreatorMapper {

    @Mapping(target = "id", ignore = true)
    Creator toEntity(CreatorRequestTo creatorRequest);

    CreatorResponseTo toDTO(Creator creator);
}