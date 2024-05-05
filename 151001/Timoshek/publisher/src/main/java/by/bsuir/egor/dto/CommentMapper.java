package by.bsuir.egor.dto;

import by.bsuir.egor.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper( CommentMapper.class );

    Comment commentResponseToToComment(CommentResponseTo responseTo);
    Comment commentRequestToToComment(CommentRequestTo requestTo);

    CommentRequestTo commentToCommentRequestTo(Comment comment);

    CommentResponseTo commentToCommentResponseTo(Comment comment);

    List<CommentResponseTo> listRequestToResponse(List<CommentRequestTo> requestList);
}
