package com.example.rvlab1.mapper;

import com.example.rvlab1.model.dto.request.IssueRequestTo;
import com.example.rvlab1.model.dto.request.IssueRequestWithIdTo;
import com.example.rvlab1.model.dto.response.IssueResponseTo;
import com.example.rvlab1.model.entity.IssueEntity;
import com.example.rvlab1.model.service.Issue;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IssueMapper {
    Issue mapToBO(IssueEntity issueEntity);

    IssueEntity mapToEntity(Issue issue);

    Issue mapToBO(IssueRequestTo issueRequestTo);

    IssueResponseTo mapToResponseTo(Issue issue);

    @InheritConfiguration
    void updateIssueRequestToToIssueBo(IssueRequestWithIdTo issueRequestWithIdTo, @MappingTarget Issue issue);
}
