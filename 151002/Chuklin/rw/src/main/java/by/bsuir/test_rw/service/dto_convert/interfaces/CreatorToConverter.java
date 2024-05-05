package by.bsuir.test_rw.service.dto_convert.interfaces;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.model.dto.creator.CreatorRequestTO;
import by.bsuir.test_rw.model.dto.creator.CreatorResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorToConverter extends ToConverter<Creator, CreatorRequestTO, CreatorResponseTO> {

    @Override
    Creator convertToEntity(CreatorRequestTO requestTo) throws ToConvertException;

    @Override
    CreatorResponseTO convertToDto(Creator entity) throws ToConvertException;
}
