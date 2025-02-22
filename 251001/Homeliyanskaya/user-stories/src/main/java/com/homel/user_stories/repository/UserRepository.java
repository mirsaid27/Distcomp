package com.homel.user_stories.repository;

import com.homel.user_stories.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User notification);
    Optional<User> findById(Long id);
    public List<User> findAll();
    void deleteById(Long id);
}
