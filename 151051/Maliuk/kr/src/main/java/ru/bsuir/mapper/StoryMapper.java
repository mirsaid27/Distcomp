package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.StoryRequestTo;
import ru.bsuir.dto.response.StoryResponseTo;
import ru.bsuir.entity.Story;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source="creatorId", target = "creator.id")
    Story toEntity(StoryRequestTo dto);

    @Mapping(source = "creator.id", target = "creatorId")
    StoryResponseTo toDTO(Story entity);
}
