package by.bsuir.mapper;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post PostRequestToPost(PostRequestTo PostRequestTo);

    PostResponseTo PostToPostResponse(Post Post);
}
