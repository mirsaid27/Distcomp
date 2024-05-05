package by.bsuir.publisher.dto.requests.converters;

import by.bsuir.publisher.domain.Issue;
import by.bsuir.publisher.dto.requests.IssueRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueRequestConverter {
    @Mapping(source = "creatorId", target = "creator.id")
    Issue fromDto(IssueRequestDto issue);
}
