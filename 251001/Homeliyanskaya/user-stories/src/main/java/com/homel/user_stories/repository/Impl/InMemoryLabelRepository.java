package com.homel.user_stories.repository.Impl;

import com.homel.user_stories.model.Label;
import com.homel.user_stories.repository.LabelRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryLabelRepository implements LabelRepository {
    private final Map<Long, Label> labels = new HashMap<>();
    private long nextId = 1;

    @Override
    public Label save(Label label) {
        if (label.getId() == null) {
            label.setId(nextId++);
        }
        labels.put(label.getId(), label);
        return label;
    }

    @Override
    public Optional<Label> findById(Long id) {
        return Optional.ofNullable(labels.get(id));
    }

    @Override
    public List<Label> findAll() {
        return new ArrayList<>(labels.values());
    }

    @Override
    public void deleteById(Long id) {
        labels.remove(id);
    }
}
