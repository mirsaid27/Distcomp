package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    @Mapping(source = "user.id", target = "userId")
    StoryResponseTo toResponse(Story story);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Story toEntity(StoryRequestTo storyRequestDto);
}