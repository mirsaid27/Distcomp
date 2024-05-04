package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Story;
import by.bsuir.publisher.dto.requests.StoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryRequestConverter {
    @Mapping(source = "editorId", target = "editor.id")
    Story fromDto(StoryRequestDto story);
}
