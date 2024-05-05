package by.bsuir.mapper;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postRequestToPost(PostRequestTo postRequestTo);

    @Mapping(target = "issueId", source = "post.issue.id")
    PostResponseTo postToPostResponse(Post post);
}
