package com.padabied.dc_rest.service;

import com.padabied.dc_rest.mapper.StoryMapper;
import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import com.padabied.dc_rest.repository.interfaces.StoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    public StoryResponseTo createStory(StoryRequestTo storyRequestDto) {
        Story story = storyMapper.toEntity(storyRequestDto);

        // Устанавливаем пользователя по ID, переданному в DTO
        User user = new User();  // Создаем экземпляр пользователя
        user.setId(storyRequestDto.getUserId());  // Устанавливаем ID из DTO
        story.setUser(user);  // Устанавливаем пользователя в историю

        story = storyRepository.save(story);

        return storyMapper.toResponse(story);
    }

    // Получить историю по id
    public StoryResponseTo getStoryById(Long id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        return storyOptional.map(storyMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    // Получить все истории
    public List<StoryResponseTo> getAllStories() {
        return storyRepository.findAll().stream()
                .map(storyMapper::toResponse)
                .toList();
    }

    public StoryResponseTo updateStory(Long id, StoryRequestTo storyRequestDto) {
        Story existingStory = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        // Проверка длины заголовка
        if (storyRequestDto.getTitle().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must be at least 2 characters long");
        }

        existingStory.setTitle(storyRequestDto.getTitle());
        existingStory.setContent(storyRequestDto.getContent());

        storyRepository.save(existingStory);
        return storyMapper.toResponse(existingStory);
    }

    // Удалить историю по id
    public void deleteStory(Long id) {
        if (!storyRepository.existsById(id)) {
            throw new RuntimeException("Story not found");
        }
        storyRepository.deleteById(id);
    }
}
