package by.bsuir.discussion.dto.requests.converters;

import by.bsuir.discussion.domain.Post;
import by.bsuir.discussion.dto.requests.PostRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostRequestConverter {
    Post fromDto(PostRequestDto post);
}
