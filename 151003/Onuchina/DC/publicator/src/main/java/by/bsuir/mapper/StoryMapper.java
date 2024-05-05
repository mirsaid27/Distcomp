package by.bsuir.mapper;

import by.bsuir.dto.StoryRequestTo;
import by.bsuir.dto.StoryResponseTo;
import by.bsuir.entities.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story storyRequestToStory (StoryRequestTo storyRequestTo);
    @Mapping(target = "authorId", source = "story.author.id")
    StoryResponseTo storyToStoryResponse(Story story);
}
