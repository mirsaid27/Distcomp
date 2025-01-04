package com.example.mapper;

import com.example.api.dto.IssueRequestTo;
import com.example.api.dto.IssueResponseTo;
import com.example.entities.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue (IssueRequestTo issueRequestTo);
    IssueResponseTo issueToIssueResponse(Issue issue);
}
