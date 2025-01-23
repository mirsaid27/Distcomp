package by.bsuir.dc.api;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    Iterable<T> getAll();
    Optional<T> getBy(ID id);
    Optional<T> save(T entity);
    Optional<T> update(T entity);
    boolean delete(T entity);
    boolean deleteById(long id);
}
