package by.bsuir.egor.dto;

import by.bsuir.egor.Entity.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IssueMapper {
    IssueMapper INSTANCE = Mappers.getMapper( IssueMapper.class );


    IssueRequestTo issueToIssueRequestTo(Issue issue);
    @Mapping(target = "editorId", source = "editor.id")
    IssueResponseTo issueToIssueResponseTo(Issue issue);

    Issue issueResponseToToIssue(IssueResponseTo responseTo);

    @Mapping(target = "editor.id", source = "editorId")
    Issue issueRequestToToIssue(IssueRequestTo requestTo);
}
