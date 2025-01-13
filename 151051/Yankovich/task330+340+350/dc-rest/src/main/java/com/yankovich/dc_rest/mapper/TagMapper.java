package com.yankovich.dc_rest.mapper;

import com.yankovich.dc_rest.model.Tag;
import com.yankovich.dc_rest.model.dto.requests.TagRequestTo;
import com.yankovich.dc_rest.model.dto.responses.TagResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseTo toResponse(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag toEntity(TagRequestTo stickerRequestDto);
}
