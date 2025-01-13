package com.yankovich.dc_rest.mapper;

import com.yankovich.dc_rest.model.Author;
import com.yankovich.dc_rest.model.dto.requests.AuthorRequestTo;
import com.yankovich.dc_rest.model.dto.responses.AuthorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseTo toResponse(Author author);

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorRequestTo userRequestDto);
}