package com.example.lab2.Mapper;

import com.example.lab2.DTO.TagRequestTo;
import com.example.lab2.DTO.TagResponseTo;
import com.example.lab2.Model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> list);

    List<TagResponseTo> toTagResponseList(List<Tag> list);
}
