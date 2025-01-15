package com.example.mapper;
import com.example.model.Tag;
import com.example.request.TagRequestTo;
import com.example.response.TagResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponseTo getResponse(Tag tag);
    List<TagResponseTo> getListResponse(Iterable<Tag> tags);
    Tag getTag(TagRequestTo tagRequestTo);
}