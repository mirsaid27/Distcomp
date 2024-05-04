package by.bsuir.publisher.dto.responses.converters;

import by.bsuir.publisher.domain.Post;
import by.bsuir.publisher.dto.responses.PostResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostResponseConverter.class)
public interface CollectionPostResponseConverter {
    List<PostResponseDto> toListDto(List<Post> posts);
}
