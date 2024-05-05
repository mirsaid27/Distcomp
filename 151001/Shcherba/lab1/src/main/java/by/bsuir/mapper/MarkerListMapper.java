package by.bsuir.mapper;

import by.bsuir.dto.MarkerRequestTo;
import by.bsuir.dto.MarkerResponseTo;
import by.bsuir.entities.Marker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MarkerMapper.class)
public interface MarkerListMapper {
    List<Marker> toMarkerList(List<MarkerRequestTo> markers);
    List<MarkerResponseTo> toMarkerResponseList(List<Marker> markers);
}
