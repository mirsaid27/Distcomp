package by.bsuir.services;

import by.bsuir.dto.MarkerRequestTo;
import by.bsuir.dto.MarkerResponseTo;
import by.bsuir.entities.Marker;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.MarkerListMapper;
import by.bsuir.mapper.MarkerMapper;
import by.bsuir.repository.MarkerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@CacheConfig(cacheNames = "markerCache")
public class MarkerService {
    @Autowired
    MarkerMapper markerMapper;
    @Autowired
    MarkerRepository markerDao;
    @Autowired
    MarkerListMapper markerListMapper;
    @Cacheable(cacheNames = "markers", key = "#id", unless = "#result == null")
    public MarkerResponseTo getMarkerById(@Min(0) Long id) throws NotFoundException {
        Optional<Marker> marker = markerDao.findById(id);
        return marker.map(value -> markerMapper.markerToMarkerResponse(value)).orElseThrow(() -> new NotFoundException("Marker not found!", 40004L));
    }
    @Cacheable(cacheNames = "markers")
    public List<MarkerResponseTo> getMarkers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Marker> markers = markerDao.findAll(pageable);
        return markerListMapper.toMarkerResponseList(markers.toList());
    }
    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo saveMarker(@Valid MarkerRequestTo marker) {
        Marker markerToSave = markerMapper.markerRequestToMarker(marker);
        return markerMapper.markerToMarkerResponse(markerDao.save(markerToSave));
    }
    @Caching(evict = { @CacheEvict(cacheNames = "markers", key = "#id"),
            @CacheEvict(cacheNames = "markers", allEntries = true) })
    public void deleteMarker(@Min(0) Long id) throws DeleteException {
        if (!markerDao.existsById(id)) {
            throw new DeleteException("Marker not found!", 40004L);
        } else {
            markerDao.deleteById(id);
        }
    }
    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo updateMarker(@Valid MarkerRequestTo marker) throws UpdateException {
        Marker markerToUpdate = markerMapper.markerRequestToMarker(marker);
        if (!markerDao.existsById(markerToUpdate.getId())) {
            throw new UpdateException("Marker not found!", 40004L);
        } else {
            return markerMapper.markerToMarkerResponse(markerDao.save(markerToUpdate));
        }
    }

    public List<MarkerResponseTo> getMarkerByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        List<Marker> marker = markerDao.findMarkersByTweetId(tweetId);
        return markerListMapper.toMarkerResponseList(marker);
    }
}
