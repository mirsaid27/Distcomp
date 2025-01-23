package ru.bsuir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsuir.dto.request.MarkerRequestTo;
import ru.bsuir.dto.response.MarkerResponseTo;
import ru.bsuir.entity.Marker;
import ru.bsuir.irepositories.IMarkerRepository;
import ru.bsuir.mapper.MarkerMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MarkerService {

    private final IMarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    @Autowired
    public MarkerService(IMarkerRepository markerRepository, MarkerMapper markerMapper) {
        this.markerRepository = markerRepository;
        this.markerMapper = markerMapper;
    }

    public MarkerResponseTo createMarker(MarkerRequestTo markerRequest) {
        Marker marker = markerMapper.toEntity(markerRequest);
        markerRepository.save(marker);
        return markerMapper.toDTO(marker);
    }

    public Optional<MarkerResponseTo> getMarkerById(Long id) {
        Optional<Marker> marker = markerRepository.findById(id);
        return marker.map(markerMapper::toDTO);
    }

    public List<MarkerResponseTo> getAllMarker(){
        return StreamSupport.stream(markerRepository.findAll().spliterator(), false)
                .map(markerMapper::toDTO)
                .toList();
    }

    public Optional<MarkerResponseTo> updateMarker(Long id, MarkerRequestTo markerRequest) {
        Optional<Marker> markerOpt = markerRepository.findById(id);
        if (markerOpt.isPresent()) {
            Marker marker = markerOpt.get();
            marker.setName(markerRequest.name());
            markerRepository.save(marker);
            return Optional.of(markerMapper.toDTO(marker));
        }
        return Optional.empty();
    }

    public boolean deleteMarker(Long id) {
        if(markerRepository.existsById(id)) {
            markerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
