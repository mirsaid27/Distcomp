package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Post;
import by.bsuir.publisher.dto.requests.PostRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostRequestConverter {
    Post fromDto(PostRequestDto post);
}
