package com.example.mapper;

import com.example.dto.IssueRequestTo;
import com.example.dto.IssueResponseTo;
import com.example.entities.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue (IssueRequestTo issueRequestTo);
    @Mapping(target = "editorId", source = "issue.editor.id")
    IssueResponseTo issueToIssueResponse(Issue issue);
}
