package org.example.dc.controllers;

import org.example.dc.model.MarkerDto;
import org.example.dc.services.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1.0/markers")
public class MarkersController {
    @Autowired
    private MarkerService markerService;

    @GetMapping
    public List<MarkerDto> getMarkers() {
        return markerService.getMarkers();
    }

    @GetMapping("/{id}")
    public MarkerDto getMarker(@PathVariable int id, HttpServletResponse response) {
        response.setStatus(200);
        return markerService.getMarkerById(id);
    }

    @PostMapping
    public MarkerDto createMarker(@RequestBody @Valid MarkerDto markerDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
            response.setStatus(400);
            return new MarkerDto();
        }
        response.setStatus(201);
        try {
            return markerService.createMarker(markerDto);
        } catch (Exception e) {
            response.setStatus(403);
            return markerService.getMarkers().stream()
                    .filter(t -> t.getName().equals(markerDto.getName()))
                    .findFirst().orElse(new MarkerDto());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMarker(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            markerService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public MarkerDto updateMarker(@RequestBody @Valid MarkerDto marker, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(402);
            return markerService.getMarkerById(marker.getId());
        }
        response.setStatus(200);
        return markerService.updateMarker(marker);
    }
}
