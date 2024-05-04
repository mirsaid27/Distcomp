package by.bsuir.discussion.dto.responses.converters;

import by.bsuir.discussion.domain.Post;
import by.bsuir.discussion.dto.responses.PostResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostResponseConverter.class)
public interface CollectionPostResponseConverter {
    List<PostResponseDto> toListDto(List<Post> posts);
}
