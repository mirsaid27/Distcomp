package org.example.tweetapi.mapper;

import org.example.tweetapi.model.dto.request.AuthorRequestTo;
import org.example.tweetapi.model.dto.response.AuthorResponseTo;
import org.example.tweetapi.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponseTo toResponse(Author author);

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorRequestTo userRequestDto);
}
