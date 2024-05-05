package com.example.publicator.service;

import com.example.publicator.dto.MarkerRequestTo;
import com.example.publicator.dto.MarkerResponseTo;
import com.example.publicator.exception.NotFoundException;
import com.example.publicator.mapper.MarkerListMapper;
import com.example.publicator.mapper.MarkerMapper;
import com.example.publicator.model.Marker;
import com.example.publicator.repository.MarkerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class MarkerService {
    @Autowired
    MarkerMapper markerMapper;
    @Autowired
    MarkerListMapper markerListMapper;
    @Autowired
    //MarkerDao markerDao;
    MarkerRepository markerRepository;

    public MarkerResponseTo create(@Valid MarkerRequestTo markerRequestTo){
        return markerMapper.markerToMarkerResponse(markerRepository.save(markerMapper.markerRequestToMarker(markerRequestTo)));
    }

    public List<MarkerResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        Page<Marker> res = markerRepository.findAll(p);
        return markerListMapper.toMarkerResponseList(res.toList());
    }

    public MarkerResponseTo read(@Min(0) int id) throws NotFoundException{
        if(markerRepository.existsById(id)){
            MarkerResponseTo marker = markerMapper.markerToMarkerResponse(markerRepository.getReferenceById(id));
            return marker;
        }
        else
            throw new NotFoundException("Marker not found",404);
    }

    public MarkerResponseTo update(@Valid MarkerRequestTo markerRequestTo, @Min(0) int id) throws NotFoundException{
        if(markerRepository.existsById(id)){
            Marker marker = markerMapper.markerRequestToMarker(markerRequestTo);
            marker.setId(id);
            return markerMapper.markerToMarkerResponse(markerRepository.save(marker));
        }
        else
            throw new NotFoundException("Marker not found",404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(markerRepository.existsById(id)){
            markerRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Marker not found",404);
    }



}
