package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Post;
import by.bsuir.publisher.dto.responses.PostResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostResponseConverter {
    PostResponseDto toDto(Post post);
    Post fromDto(PostResponseDto postResponseDto);
}
