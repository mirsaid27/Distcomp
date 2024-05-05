package com.example.publicator.mapper;

import com.example.publicator.dto.MarkerRequestTo;
import com.example.publicator.dto.MarkerResponseTo;
import com.example.publicator.model.Marker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MarkerMapper.class)
public interface MarkerListMapper {
    List<Marker> toMarkerList(List<MarkerRequestTo> list);

    List<MarkerResponseTo> toMarkerResponseList(List<Marker> list);
}
