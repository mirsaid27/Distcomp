package by.bsuir.discussion.dto.responses.converters;

import by.bsuir.discussion.domain.Post;
import by.bsuir.discussion.dto.responses.PostResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostResponseConverter {
    PostResponseDto toDto(Post post);
}
