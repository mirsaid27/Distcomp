package com.homel.user_stories.repository;

import com.homel.user_stories.model.Story;
import com.homel.user_stories.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StoryRepository {
    Story save(Story notification);
    Optional<Story> findById(Long id);
    public List<Story> findAll();
    void deleteById(Long id);
}
