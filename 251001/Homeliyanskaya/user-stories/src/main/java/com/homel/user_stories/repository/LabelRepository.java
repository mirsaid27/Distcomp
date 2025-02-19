package com.homel.user_stories.repository;

import com.homel.user_stories.model.Label;
import com.homel.user_stories.model.Story;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository {
    Label save(Label notification);
    Optional<Label> findById(Long id);
    public List<Label> findAll();
    void deleteById(Long id);
}
