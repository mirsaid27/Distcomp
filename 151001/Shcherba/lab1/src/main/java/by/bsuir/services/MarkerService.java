package by.bsuir.services;

import by.bsuir.dao.MarkerDao;
import by.bsuir.dto.MarkerRequestTo;
import by.bsuir.dto.MarkerResponseTo;
import by.bsuir.entities.Marker;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.MarkerListMapper;
import by.bsuir.mapper.MarkerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MarkerService {
    @Autowired
    MarkerMapper markerMapper;
    @Autowired
    MarkerDao markerDao;
    @Autowired
    MarkerListMapper markerListMapper;

    public MarkerResponseTo getMarkerById(@Min(0) Long id) throws NotFoundException{
        Optional<Marker> marker = markerDao.findById(id);
        return marker.map(value -> markerMapper.markerToMarkerResponse(value)).orElseThrow(() -> new NotFoundException("Marker not found!", 40004L));
    }

    public List<MarkerResponseTo> getMarkers() {
        return markerListMapper.toMarkerResponseList(markerDao.findAll());
    }

    public MarkerResponseTo saveMarker(@Valid MarkerRequestTo marker) {
        Marker markerToSave = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.save(markerToSave));
    }

    public void deleteMarker(@Min(0) Long id) throws DeleteException {
        markerDao.delete(id);
    }

    public MarkerResponseTo updateMarker(@Valid MarkerRequestTo marker) throws UpdateException {
        Marker markerToUpdate = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.update(markerToUpdate));
    }
}
