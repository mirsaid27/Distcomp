package com.rmakovetskij.dc_rest.mapper;

import com.rmakovetskij.dc_rest.model.Issue;
import com.rmakovetskij.dc_rest.model.dto.requests.IssueRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.IssueResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    @Mapping(source = "editor.id", target = "editorId")
    IssueResponseTo toResponse(Issue issue);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Issue toEntity(IssueRequestTo issueRequestDto);
}