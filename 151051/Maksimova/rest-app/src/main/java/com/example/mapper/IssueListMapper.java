package com.example.mapper;

import com.example.api.dto.IssueRequestTo;
import com.example.api.dto.IssueResponseTo;
import com.example.entities.Issue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> issues);
    List<IssueResponseTo> toIssueResponseList(List<Issue> issues);
}
