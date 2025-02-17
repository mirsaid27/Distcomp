package com.example.rest.service;

import com.example.rest.dto.requestDto.MessageRequestTo;
import com.example.rest.dto.responseDto.MessageResponseTo;
import com.example.rest.mapper.MessageMapper;
import com.example.rest.repository.MessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;

    public List<MessageResponseTo> getAll() {
        return messageRepo
                .getAll()
                .map(messageMapper::ToMessageResponseTo)
                .toList();
    }
    public MessageResponseTo get(long id) {
        return messageRepo
                .get(id)
                .map(messageMapper::ToMessageResponseTo)
                .orElse(null);
    }
    public MessageResponseTo create(MessageRequestTo input) {
        return messageRepo
                .create(messageMapper.ToMessage(input))
                .map(messageMapper::ToMessageResponseTo)
                .orElseThrow();
    }
    public MessageResponseTo update(MessageRequestTo input) {
        return messageRepo
                .update(messageMapper.ToMessage(input))
                .map(messageMapper::ToMessageResponseTo)
                .orElseThrow();
    }
    public boolean delete(long id) {
        return messageRepo.delete(id);
    }
}
