package by.bsuir.mapper;

import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.entities.Label;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    Label labelRequestToLabel (LabelRequestTo labelRequestTo);
    LabelResponseTo labelToLabelResponse(Label label);
}

