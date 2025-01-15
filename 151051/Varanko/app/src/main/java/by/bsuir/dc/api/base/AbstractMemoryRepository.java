package by.bsuir.dc.api.base;

import by.bsuir.dc.api.CrudRepository;
import by.bsuir.dc.api.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractMemoryRepository<T extends Entity> extends JpaRepository<T, Long> {
//    private final static AtomicLong ids = new AtomicLong();
//    protected final Map<Long, T> map = new HashMap<>();

    public default Iterable<T> getAll() { return findAll(); }

    public default Optional<T> getBy(Long id) { return findById(id); }

    public default Optional<T> create(T entity) {
        return Optional.of(save(entity));
    }
    public default Optional<T> update(T entity) {
        return Optional.of(saveAndFlush(entity));
    }
    public default boolean remove(T entity) {
        delete(entity);
        return true;
    }
}
