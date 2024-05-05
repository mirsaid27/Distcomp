package by.bsuir.mapper;

import by.bsuir.dto.MarkerRequestTo;
import by.bsuir.dto.MarkerResponseTo;
import by.bsuir.entities.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    Marker markerRequestToMarker (MarkerRequestTo markerRequestTo);
    MarkerResponseTo markerToMarkerResponse(Marker marker);
}

