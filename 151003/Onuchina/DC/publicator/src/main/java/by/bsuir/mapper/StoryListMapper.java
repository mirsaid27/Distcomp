package by.bsuir.mapper;

import by.bsuir.dto.StoryRequestTo;
import by.bsuir.dto.StoryResponseTo;
import by.bsuir.entities.Story;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StoryMapper.class)
public interface StoryListMapper {
    List<Story> toStoryList(List<StoryRequestTo> stories);
    List<StoryResponseTo> toStoryResponseList(List<Story> stories);
}

