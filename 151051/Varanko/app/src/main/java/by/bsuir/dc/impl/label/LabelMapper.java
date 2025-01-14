package by.bsuir.dc.impl.label;

import by.bsuir.dc.impl.label.model.Label;
import by.bsuir.dc.impl.label.model.LabelRequest;
import by.bsuir.dc.impl.label.model.LabelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface LabelMapper {
    LabelResponse toLabelResponseDto(Label label);
    List<LabelResponse> toLabelResponseDto(Iterable<Label> label);
    List<LabelResponse> toLabelRequestDto(Iterable<Label> label);
    LabelRequest toLabelRequestDto(Label label);
    Label toLabelDto(LabelRequest request);
    List<Label> toLabelDto(Iterable<LabelRequest> requests);
}
