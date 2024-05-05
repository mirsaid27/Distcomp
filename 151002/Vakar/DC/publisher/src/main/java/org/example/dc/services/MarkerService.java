package org.example.dc.services;

import org.example.dc.model.MarkerDto;

import java.util.List;

public interface MarkerService {
    List<MarkerDto> getMarkers();

    MarkerDto getMarkerById(int id);

    boolean delete(int id) throws Exception;

    MarkerDto updateMarker(MarkerDto markerDto);

    MarkerDto createMarker(MarkerDto markerDto);
}
