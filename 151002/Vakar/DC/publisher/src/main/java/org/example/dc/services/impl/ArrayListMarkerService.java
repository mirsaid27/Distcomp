package org.example.dc.services.impl;

import org.example.dc.model.MarkerDto;
import org.example.dc.services.MarkerService;

import java.util.ArrayList;
import java.util.List;

public class ArrayListMarkerService implements MarkerService {
    private static int id = 1;
    private List<MarkerDto> markers = new ArrayList<>();
    @Override
    public List<MarkerDto> getMarkers() {
        return markers;
    }

    @Override
    public MarkerDto getMarkerById(int id) {
        return markers.stream()
                .filter(tag -> tag.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean delete(int id) throws Exception {
        MarkerDto tag = markers.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        markers.remove(tag);
        return true;
    }

    @Override
    public MarkerDto updateMarker(MarkerDto markerDto) {
        MarkerDto marker = markers.stream()
                .filter(t -> t.getId() == markerDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        marker.setName(markerDto.getName());
        return markerDto;
    }

    @Override
    public MarkerDto createMarker(MarkerDto markerDto) {
        markerDto.setId(id++);
        markers.add(markerDto);
        return markerDto;
    }
}
