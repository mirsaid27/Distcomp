package com.example.rest.repository;

import com.example.rest.entity.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public class UserRepo implements Repository<User> {
    Map<Long, User> memoryDatabase = new ConcurrentHashMap<>();
    public static final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Stream<User> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<User> create(User input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<User> update(User input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memoryDatabase.remove(id)!=null;
    }
}
