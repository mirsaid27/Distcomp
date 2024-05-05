package by.bsuir.mapper;

import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.entities.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<Tag> toTagList(List<TagRequestTo> Tags);
    List<TagResponseTo> toTagResponseList(List<Tag> Tags);
}
