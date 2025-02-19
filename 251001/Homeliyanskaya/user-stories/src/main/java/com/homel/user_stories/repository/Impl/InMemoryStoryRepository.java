package com.homel.user_stories.repository.Impl;

import com.homel.user_stories.model.Story;
import com.homel.user_stories.repository.StoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryStoryRepository implements StoryRepository {
    private final Map<Long, Story> stories = new HashMap<>();
    private long nextId = 1;

    @Override
    public Story save(Story story) {
        if (story.getId() == null) {
            story.setId(nextId++);
        }
        stories.put(story.getId(), story);
        return story;
    }

    @Override
    public Optional<Story> findById(Long id) {
        return Optional.ofNullable(stories.get(id));
    }

    @Override
    public List<Story> findAll() {
        return new ArrayList<>(stories.values());
    }

    @Override
    public void deleteById(Long id) {
        stories.remove(id);
    }
}
