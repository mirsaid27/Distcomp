package by.harlap.rest.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    T save(T entity);

    T update(T entity);

}
