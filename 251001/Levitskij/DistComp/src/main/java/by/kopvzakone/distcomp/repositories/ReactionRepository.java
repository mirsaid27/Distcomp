package by.kopvzakone.distcomp.repositories;

import by.kopvzakone.distcomp.entities.Reaction;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public class ReactionRepository implements Repo<Reaction> {
    private final Map<Long, Reaction> memDatabase = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    public Stream<Reaction> getAll() {
        return memDatabase.values().stream();
    }

    @Override
    public Optional<Reaction> get(long id) {
        return Optional.ofNullable(memDatabase.get(id));
    }

    @Override
    public Optional<Reaction> create(Reaction input) {
        long id = counter.incrementAndGet();
        input.setId(id);
        memDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Reaction> update(Reaction input) {
        memDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memDatabase.remove(id) != null;
    }
}
