package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.CCommentRequestTo;
import ru.bsuir.dto.response.CCommentResponseTo;
import ru.bsuir.entity.CComment;

@Mapper(componentModel = "spring")
public interface CCommentMapper {

    @Mapping(source = "tweetId", target = "tweetId")
    CComment toEntity(CCommentRequestTo dto);


    @Mapping(source = "tweetId", target = "tweetId")
    CCommentResponseTo toDTO(CComment entity);
}

