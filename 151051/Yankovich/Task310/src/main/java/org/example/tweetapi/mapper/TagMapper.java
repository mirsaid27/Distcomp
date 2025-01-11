package org.example.tweetapi.mapper;

import org.example.tweetapi.model.dto.request.TagRequestTo;
import org.example.tweetapi.model.dto.response.TagResponseTo;
import org.example.tweetapi.model.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseTo toResponse(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag toEntity(TagRequestTo tagRequestDto);
}