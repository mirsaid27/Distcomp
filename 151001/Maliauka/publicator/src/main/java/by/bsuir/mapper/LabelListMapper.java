package by.bsuir.mapper;

import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.entities.Label;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LabelMapper.class)
public interface LabelListMapper {
    List<Label> toLabelList(List<LabelRequestTo> labels);
    List<LabelResponseTo> toLabelResponseList(List<Label> labels);
}
