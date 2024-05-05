package com.example.discussion.mapper;

import com.example.discussion.model.entity.Message;
import com.example.discussion.model.response.MessageResponseTo;
import org.springframework.stereotype.Service;

@Service
public class MessageMapperOwn {
    public MessageResponseTo entityToResponse(Message entity) {
        return MessageResponseTo.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .tweetId(entity.getTweetId())
                .build();
    }
}
