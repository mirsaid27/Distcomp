package org.example.discussion.mapper;

import org.example.discussion.dto.requestDto.MessageRequestTo;
import org.example.discussion.dto.responseDto.MessageResponseTo;
import org.example.discussion.dto.updateDto.MessageUpdateTo;
import org.example.discussion.entity.Message;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageResponseTo ToMessageResponseTo(Message Message);

    Message ToMessage(MessageRequestTo MessageRequestTo);

    Message ToMessage(MessageUpdateTo MessageUpdateTo);
}
