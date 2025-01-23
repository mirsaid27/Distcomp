package by.bsuir.dc.impl.story;

import by.bsuir.dc.impl.story.model.Story;
import by.bsuir.dc.impl.story.model.StoryRequest;
import by.bsuir.dc.impl.story.model.StoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface StoryMapper {
    StoryResponse toStoryResponseDto(Story Story);
    List<StoryResponse> toStoryResponseDto(Iterable<Story> Story);
    List<StoryResponse> toStoryRequestDto(Iterable<Story> Story);
    StoryRequest toStoryRequestDto(Story Story);
    @Mapping(target = "createdDatetime", ignore = true)
    @Mapping(target = "modifiedDatetime", ignore = true)
    Story toStoryDto(StoryRequest request);
    List<Story> toStoryDto(Iterable<StoryRequest> requests);
}
