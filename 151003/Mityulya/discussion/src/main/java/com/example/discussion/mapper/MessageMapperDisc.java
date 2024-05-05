package com.example.discussion.mapper;

import com.example.discussion.model.entity.Message;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapperDisc {
    MessageResponseTo entityToResponse(Message entity);
    Message requestToEntity(MessageRequestTo request);
}
