package com.example.rest.repository;

import com.example.rest.entity.Message;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public class MessageRepo implements Repository<Message> {
    Map<Long, Message> memoryDatabase = new ConcurrentHashMap<>();
    public static final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Stream<Message> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Message> get(long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Message> create(Message input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Message> update(Message input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memoryDatabase.remove(id)!=null;
    }
}
