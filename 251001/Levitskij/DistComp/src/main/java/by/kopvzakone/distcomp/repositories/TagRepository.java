package by.kopvzakone.distcomp.repositories;

import by.kopvzakone.distcomp.entities.Tag;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


@Repository
public class TagRepository implements Repo<Tag> {
    private final Map<Long, Tag> memDatabase = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    public Stream<Tag> getAll() {
        return memDatabase.values().stream();
    }

    @Override
    public Optional<Tag> get(long id) {
        return Optional.ofNullable(memDatabase.get(id));
    }

    @Override
    public Optional<Tag> create(Tag input) {
        long id = counter.incrementAndGet();
        input.setId(id);
        memDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Tag> update(Tag input) {
        memDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memDatabase.remove(id) != null;
    }
}
