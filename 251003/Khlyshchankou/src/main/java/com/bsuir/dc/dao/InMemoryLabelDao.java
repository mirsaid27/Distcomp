package com.bsuir.dc.dao;

import com.bsuir.dc.model.Label;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryLabelDao {
    private final Map<Long, Label> labels = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Label save(Label label) {
        if (label.getId() == 0) {
            label.setId(idGenerator.getAndIncrement());
        }
        labels.put(label.getId(), label);
        return label;
    }

    public List<Label> findAll() {
        return new ArrayList<>(labels.values());
    }
    public Optional<Label> findById(long id) {
        return Optional.ofNullable(labels.get(id));
    }

    public void deleteById(long id) {
        labels.remove(id);
    }
}
