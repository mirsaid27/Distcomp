package com.example.publicator.mapper;

import com.example.publicator.dto.MarkerRequestTo;
import com.example.publicator.dto.MarkerResponseTo;
import com.example.publicator.model.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    Marker markerRequestToMarker(MarkerRequestTo markerRequestTo);

    MarkerResponseTo markerToMarkerResponse(Marker marker);
}
