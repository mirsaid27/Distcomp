package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.CommentRequestTo;
import ru.bsuir.dto.response.CommentResponseTo;
import ru.bsuir.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentRequestTo dto);

    @Mapping(source="tweet.id", target = "tweetId")
    CommentResponseTo toDTO(Comment entity);
}

