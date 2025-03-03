package by.molchan.Distcomp.utils.mappers;

import by.molchan.Distcomp.DTOs.Requests.CommentRequestDTO;

import by.molchan.Distcomp.DTOs.Responses.CommentResponseDTO;
import by.molchan.Distcomp.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentsMapper {

    @Mapping(target = "articleId", expression = "java(comment.getArticle().getId())")
    CommentResponseDTO toCommentResponse(Comment comment);
    List<CommentResponseDTO> toCommentResponseList(List<Comment> comments);
    Comment toComment(CommentRequestDTO commentRequestDTO);
}
