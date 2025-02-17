package com.example.rest.mapper;
import com.example.rest.dto.requestDto.MessageRequestTo;
import com.example.rest.dto.responseDto.MessageResponseTo;
import com.example.rest.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageResponseTo ToMessageResponseTo(Message Message);
    Message ToMessage(MessageRequestTo MessageRequestTo);
}
