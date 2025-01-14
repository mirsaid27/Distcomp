package by.bsuir.dc.api.base;

import by.bsuir.dc.api.CrudRepository;
import by.bsuir.dc.api.Entity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public abstract class AbstractMemoryRepository<T extends Entity> implements CrudRepository<T, Long>{
    private final static AtomicLong ids = new AtomicLong();
    protected final Map<Long, T> map = new HashMap<>();

    @Override
    public Iterable<T> getAll() { return map.values(); }

    @Override
    public Optional<T> getBy(Long id) { return Optional.ofNullable(map.get(id)); }

    @Override
    public Optional<T> save(T entity) {
        long id = ids.incrementAndGet();
        entity.setId(id);
        map.put(id, entity);
        return Optional.of(entity);
    }

    @Override
    public boolean delete(T entity) { return map.remove(entity.getId()) != null; }
}
