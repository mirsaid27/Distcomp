package by.bsuir.test_rw.service.dto_convert.interfaces;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.model.dto.tag.TagRequestTO;
import by.bsuir.test_rw.model.dto.tag.TagResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagToConverter extends ToConverter<Tag, TagRequestTO, TagResponseTO> {

    @Override
    Tag convertToEntity(TagRequestTO requestTo) throws ToConvertException;

    @Override
    TagResponseTO convertToDto(Tag entity) throws ToConvertException;
}
