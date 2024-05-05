package by.harlap.rest.repository.impl;

import by.harlap.rest.model.AbstractEntity;
import by.harlap.rest.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public abstract class AbstractInMemoryRepository<T extends AbstractEntity> implements AbstractRepository<T, Long> {

    private final Map<Long, T> MAP = new HashMap<>();
    private final AtomicLong ID_HOLDER = new AtomicLong();

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(MAP.get(id));
    }

    @Override
    public List<T> findAll() {
        return MAP.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        MAP.remove(id);
    }

    @Override
    public T save(T t) {
        final long id = ID_HOLDER.incrementAndGet();
        t.setId(id);
        MAP.put(id, t);
        return t;
    }

    @Override
    public T update(T t) {
        MAP.put(t.getId(), t);
        return t;
    }
}
