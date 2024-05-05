package by.harlap.jpa.mapper;

import by.harlap.jpa.dto.request.CreateTagDto;
import by.harlap.jpa.dto.request.UpdateTagDto;
import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.model.Tag;
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
