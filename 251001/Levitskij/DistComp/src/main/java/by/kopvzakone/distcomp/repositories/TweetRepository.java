package by.kopvzakone.distcomp.repositories;

import by.kopvzakone.distcomp.entities.Tweet;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public class TweetRepository implements Repo<Tweet> {
    private final Map<Long, Tweet> memDatabase = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    public Stream<Tweet> getAll() {
        return memDatabase.values().stream();
    }

    @Override
    public Optional<Tweet> get(long id) {
        return Optional.ofNullable(memDatabase.get(id));
    }

    @Override
    public Optional<Tweet> create(Tweet input) {
        long id = counter.incrementAndGet();
        input.setId(id);
        input.setCreated(Timestamp.from(Instant.now()));
        input.setModified(Timestamp.from(Instant.now()));
        memDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Tweet> update(Tweet input) {
        input.setModified(Timestamp.from(Instant.now()));
        memDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memDatabase.remove(id) != null;
    }
}
