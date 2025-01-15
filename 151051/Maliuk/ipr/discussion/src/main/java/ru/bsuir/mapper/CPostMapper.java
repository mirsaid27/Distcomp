package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.CPostRequestTo;
import ru.bsuir.dto.response.CPostResponseTo;
import ru.bsuir.entity.CPost;

@Mapper(componentModel = "spring")
public interface CPostMapper {

    @Mapping(source = "storyId", target = "storyId")
    CPost toEntity(CPostRequestTo dto);


    @Mapping(source = "storyId", target = "storyId")
    CPostResponseTo toDTO(CPost entity);
}

