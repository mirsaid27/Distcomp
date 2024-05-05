package by.bsuir.news.service;

import by.bsuir.news.dto.request.MarkerRequestTo;
import by.bsuir.news.dto.response.MarkerResponseTo;
import by.bsuir.news.entity.Marker;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.MarkerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@CacheConfig(cacheNames = "markersCache")
@RequiredArgsConstructor
public class MarkerService {
    @Autowired
    private MarkerRepository markerRepository;

    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo create(MarkerRequestTo request) {
        return MarkerResponseTo.toResponse(markerRepository.save(MarkerRequestTo.fromRequest(request)));
    }

    @Cacheable(cacheNames = "markers")
    public List<MarkerResponseTo> getAll() {
        return markerRepository.findAll().stream().map(MarkerResponseTo::toResponse).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "markers", key = "#id", unless = "#result == null")
    public MarkerResponseTo getById(Long id) throws ClientException {
        Optional<Marker> marker = markerRepository.findById(id);
        if(marker.isEmpty()) {
            throw new ClientException("No marker with such id");
        }
        return MarkerResponseTo.toResponse(marker.get());
    }

    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo update(MarkerRequestTo request) throws ClientException{
        Optional<Marker> marker = markerRepository.findById(request.getId());
        if(marker.isEmpty()) {
            throw new ClientException("No marker with such id");
        }
        return MarkerResponseTo.toResponse(markerRepository.save(MarkerRequestTo.fromRequest(request)));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "markers", key = "#id"),
            @CacheEvict(cacheNames = "markers", allEntries = true) })
    public Long delete(Long id) throws ClientException {
        if(markerRepository.findById(id).isEmpty()) {
            throw new ClientException("No marker with such id");
        }
        markerRepository.deleteById(id);
        if(markerRepository.findById(id).isPresent()) {
            throw new ClientException("Failed to delete the marker");
        }
        return id;
    }
}
