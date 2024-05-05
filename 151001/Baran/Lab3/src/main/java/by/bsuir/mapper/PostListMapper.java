package by.bsuir.mapper;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface PostListMapper {
    List<Post> toPostList(List<PostRequestTo> posts);
    List<PostResponseTo> toPostResponseList(List<Post> posts);
}
