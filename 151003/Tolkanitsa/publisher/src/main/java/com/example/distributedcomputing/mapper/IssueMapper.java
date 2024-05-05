package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Creator;
import com.example.distributedcomputing.model.entity.Issue;
import com.example.distributedcomputing.model.request.IssueRequestTo;
import com.example.distributedcomputing.model.response.IssueResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue dtoToEntity(IssueRequestTo issueRequestTo);
    List<Creator> dtoToEntity(Iterable<Issue> stories);

    IssueResponseTo entityToDto(Issue issue);

    List<IssueResponseTo> entityToDto(Iterable<Issue> stories);
}
