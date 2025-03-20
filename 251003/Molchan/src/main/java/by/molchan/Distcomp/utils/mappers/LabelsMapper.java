package by.molchan.Distcomp.utils.mappers;

import by.molchan.Distcomp.DTOs.Requests.LabelRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.LabelResponseDTO;
import by.molchan.Distcomp.models.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LabelsMapper {
    LabelResponseDTO toLabelResponse(Label label);
    List<LabelResponseDTO> toLabelResponseList(List<Label> labels);
    Label toLabel(LabelRequestDTO labelRequestDTO);
    List<Label> toLabelList(List<LabelRequestDTO> labelRequestDTOList);
}
