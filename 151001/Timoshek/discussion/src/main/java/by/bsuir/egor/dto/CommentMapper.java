package by.bsuir.egor.dto;

import by.bsuir.egor.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper( CommentMapper.class );

    Comment postResponseToToPost(CommentResponseTo responseTo);

    Comment postRequestToToPost(CommentRequestTo requestTo);

    CommentRequestTo postToPostRequestTo(Comment comment);

    CommentResponseTo postToPostResponseTo(Comment comment);
}
