package by.bsuir.distcomp.dto.mapper;

import by.bsuir.distcomp.dto.request.MarkerRequestTo;
import by.bsuir.distcomp.dto.response.MarkerResponseTo;
import by.bsuir.distcomp.entity.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    MarkerResponseTo toDto(Marker marker);
    Marker toEntity(MarkerRequestTo markerDTO);
}
