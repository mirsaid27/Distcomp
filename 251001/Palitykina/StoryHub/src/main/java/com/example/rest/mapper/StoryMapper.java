package com.example.rest.mapper;
import com.example.rest.dto.requestDto.StoryRequestTo;
import com.example.rest.dto.responseDto.StoryResponseTo;
import com.example.rest.entity.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    StoryResponseTo ToStoryResponseTo(Story Story);
    Story ToStory(StoryRequestTo StoryRequestTo);
}
