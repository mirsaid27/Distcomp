package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Story;
import by.bsuir.publisher.dto.responses.StoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryResponseConverter {
    @Mapping(source = "user.id", target = "userId")
    StoryResponseDto toDto(Story story);
}
