package com.example.lab2.Mapper;

import com.example.lab2.DTO.IssueRequestTo;
import com.example.lab2.DTO.IssueResponseTo;
import com.example.lab2.Model.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue(IssueRequestTo issueRequestTo);
    @Mapping(target = "creatorId", source = "issue.creator.id")

    IssueResponseTo issueToIssueResponse(Issue issue);
}
