package com.example.rest.repository;

import com.example.rest.entity.Label;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public class LabelRepo implements Repository<Label> {
    Map<Long, Label> memoryDatabase = new ConcurrentHashMap<>();
    public static final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Stream<Label> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Label> get(long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Label> create(Label input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Label> update(Label input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memoryDatabase.remove(id)!=null;
    }
}
