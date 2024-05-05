package com.example.lab2.Mapper;

import com.example.lab2.DTO.IssueRequestTo;
import com.example.lab2.DTO.IssueResponseTo;
import com.example.lab2.Model.Issue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IssueMapper.class)
public interface IssueListMapper {
    List<Issue> toIssueList(List<IssueRequestTo> list);

    List<IssueResponseTo> toIssueResponseList(List<Issue> list);
}
