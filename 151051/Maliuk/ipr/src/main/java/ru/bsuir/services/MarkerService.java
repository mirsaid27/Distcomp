package ru.bsuir.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bsuir.dto.request.MarkerRequestTo;
import ru.bsuir.dto.response.MarkerResponseTo;
import ru.bsuir.entity.Marker;
import ru.bsuir.irepositories.IMarkerRepository;
import ru.bsuir.mapper.MarkerMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableCaching
public class MarkerService {

    private final IMarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    @CacheEvict(value = "markers", allEntries = true)
    public MarkerResponseTo createMarker(MarkerRequestTo markerRequest) {
        Marker marker = markerMapper.toEntity(markerRequest);
        marker = markerRepository.save(marker);
        return markerMapper.toDTO(marker);
    }

    @Cacheable(value = "markers", key = "#id")
    public MarkerResponseTo getMarkerById(Long id) {
        Optional<Marker> markerOpt = markerRepository.findById(id);
        return markerOpt.map(markerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Marker not found"));
    }

    @Cacheable(value = "markersList")
    public List<MarkerResponseTo> getAllMarker(){
        return markerRepository.findAll().stream()
                .map(markerMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "markers", key = "#id")
    public MarkerResponseTo updateMarker(Long id, MarkerRequestTo markerRequest) {

        Marker marker = markerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marker not found"));

        marker.setName(markerRequest.getName());
        markerRepository.save(marker);

        return markerMapper.toDTO(marker);
    }

    @CacheEvict(value = "markers", key = "#id")
    public void deleteMarker(Long id) {
        if(!markerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marker not found");
        }
        markerRepository.deleteById(id);
    }
}
