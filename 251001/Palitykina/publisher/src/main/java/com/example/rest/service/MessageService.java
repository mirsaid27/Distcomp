package com.example.rest.service;

import com.example.rest.dto.requestDto.MessageRequestTo;
import com.example.rest.dto.responseDto.MessageResponseTo;
import com.example.rest.dto.updateDto.MessageUpdateTo;
import com.example.rest.entity.Message;
import com.example.rest.entity.Story;
import com.example.rest.exeption.StoryNotFoundException;
import com.example.rest.mapper.MessageMapper;
import com.example.rest.repository.MessageRepo;
import com.example.rest.repository.StoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;
    private final StoryRepo storyRepo;

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
        Story story = storyRepo.findById(input.getStoryId()).orElseThrow(()-> new StoryNotFoundException("Story with id " + input.getStoryId() + " not found"));
        Message message = messageMapper.ToMessage(input);
        message.setStory(story);
        return messageRepo
                .create(message)
                .map(messageMapper::ToMessageResponseTo)
                .orElseThrow();
    }
    public MessageResponseTo update(MessageUpdateTo input) {
        Story story = storyRepo.findById(input.getStoryId()).orElseThrow(()-> new StoryNotFoundException("Story with id " + input.getStoryId() + " not found"));
        Message message = messageMapper.ToMessage(input);
        message.setStory(story);
        return messageRepo
                .update(message)
                .map(messageMapper::ToMessageResponseTo)
                .orElseThrow();
    }
    public boolean delete(long id) {
         return messageRepo.delete(id);
    }
}
