package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.PostRequestTo;
import ru.bsuir.dto.response.PostResponseTo;
import ru.bsuir.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source="storyId", target = "story.id")
    Post toEntity(PostRequestTo dto);

    @Mapping(source="story.id", target = "storyId")
    PostResponseTo toDTO(Post entity);
}

