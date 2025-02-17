package com.example.rest.repository;

import com.example.rest.entity.Story;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public class StoryRepo implements Repository<Story> {
    Map<Long, Story> memoryDatabase = new ConcurrentHashMap<>();
    public static final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Stream<Story> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Story> get(long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Story> create(Story input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Story> update(Story input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memoryDatabase.remove(id)!=null;
    }
}
