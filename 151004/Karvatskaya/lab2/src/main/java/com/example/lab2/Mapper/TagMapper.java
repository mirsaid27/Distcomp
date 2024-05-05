package com.example.lab2.Mapper;

import com.example.lab2.DTO.TagRequestTo;
import com.example.lab2.DTO.TagResponseTo;
import com.example.lab2.Model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag(TagRequestTo tagRequestTo);

    TagResponseTo tagToTagResponse(Tag tag);
}
