package com.example.rvlab1.mapper;

import com.example.rvlab1.model.dto.request.LabelRequestTo;
import com.example.rvlab1.model.dto.request.LabelRequestWithIdTo;
import com.example.rvlab1.model.dto.request.PostRequestWithIdTo;
import com.example.rvlab1.model.dto.response.LabelResponseTo;
import com.example.rvlab1.model.entity.LabelEntity;
import com.example.rvlab1.model.service.Label;
import com.example.rvlab1.model.service.Post;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { IssueMapper.class })
public interface LabelMapper {
    Label mapToBO(LabelEntity labelEntity);

    LabelEntity mapToEntity(Label label);

    Label mapToBO(LabelRequestTo labelRequestTo);

    LabelResponseTo mapToResponseTo(Label label);

    @InheritConfiguration
    void updateLabelRequestToToLabelBo(LabelRequestWithIdTo labelRequestWithIdTo, @MappingTarget Label label);
}
