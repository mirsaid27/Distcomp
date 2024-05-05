package by.bsuir.mapper;

import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.entities.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagRequestToTag (TagRequestTo tagRequestTo);
    TagResponseTo tagToTagResponse(Tag tag);
}

