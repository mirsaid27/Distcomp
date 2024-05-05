package by.bsuir.mapper;

import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.entities.Tag;
import by.bsuir.mapper.TagMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface LabelListMapper {
    List<Tag> toTagList(List<TagRequestTo> tags);
    List<TagResponseTo> toTagResponseList(List<Tag> tags);
}
