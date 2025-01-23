package com.rmakovetskij.dc_rest.mapper;

import com.rmakovetskij.dc_rest.model.Label;
import com.rmakovetskij.dc_rest.model.dto.requests.LabelRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.LabelResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    LabelResponseTo toResponse(Label label);

    @Mapping(target = "id", ignore = true)
    Label toEntity(LabelRequestTo labelRequestDto);
}
