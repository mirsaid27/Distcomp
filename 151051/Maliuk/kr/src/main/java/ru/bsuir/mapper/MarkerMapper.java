package ru.bsuir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bsuir.dto.request.MarkerRequestTo;
import ru.bsuir.dto.response.MarkerResponseTo;
import ru.bsuir.entity.Marker;

@Mapper(componentModel = "spring")
public interface MarkerMapper {

    @Mapping(target = "id", ignore = true)
    Marker toEntity(MarkerRequestTo dto);
    MarkerResponseTo toDTO(Marker entity);
}
