package by.harlap.rest.mapper;

import by.harlap.rest.dto.request.CreateTagDto;
import by.harlap.rest.dto.request.UpdateTagDto;
import by.harlap.rest.dto.response.TagResponseDto;
import by.harlap.rest.model.Tag;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface TagMapper {
    Tag toTag(CreateTagDto tagRequest);

    TagResponseDto toTagResponse(Tag tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag toTag(UpdateTagDto tagRequest, @MappingTarget Tag tag);
}
