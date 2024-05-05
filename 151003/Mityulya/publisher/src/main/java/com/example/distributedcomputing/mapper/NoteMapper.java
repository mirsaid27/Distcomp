package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Message;
import com.example.distributedcomputing.model.request.MessageRequestTo;
import com.example.distributedcomputing.model.response.MessageResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Message dtoToEntity(MessageRequestTo messageRequestTo);
    List<Message> dtoToEntity(Iterable<MessageRequestTo> notes);

    MessageResponseTo entityToDto(Message message);

    List<MessageResponseTo> entityToDto(Iterable<Message> notes);
}
