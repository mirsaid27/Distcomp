package com.example.discussion.storage;

import java.util.List;
import java.util.Optional;

public interface CRUDStorage<T, K> {
    List<T> findAll();

    T save(T t);

    Optional<T> findById(K id);

//    T update(T T);

    void delete(T t);
}
