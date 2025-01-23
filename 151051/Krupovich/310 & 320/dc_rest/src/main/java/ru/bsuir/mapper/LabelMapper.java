package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.LabelRequestTo;
import ru.bsuir.dto.response.LabelResponseTo;
import ru.bsuir.entity.Label;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    @Mapping(target = "id", ignore = true)

    Label toEntity(LabelRequestTo dto);
    LabelResponseTo toDTO(Label entity);
}
