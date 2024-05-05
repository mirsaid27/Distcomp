package by.bsuir.mapper;

import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.entities.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue issueRequestToIssue (IssueRequestTo issueRequestTo);
    IssueResponseTo issueToIssueResponse(Issue issue);
}
