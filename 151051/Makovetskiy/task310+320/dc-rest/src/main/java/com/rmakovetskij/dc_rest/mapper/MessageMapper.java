package com.rmakovetskij.dc_rest.mapper;

import com.rmakovetskij.dc_rest.model.Message;
import com.rmakovetskij.dc_rest.model.dto.requests.MessageRequestTo;
import com.rmakovetskij.dc_rest.model.dto.responses.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "issue.id", target = "issueId")
    MessageResponseTo toResponse(Message message);

    @Mapping(target = "id", ignore = true)
    Message toEntity(MessageRequestTo messageRequestDto);
}
