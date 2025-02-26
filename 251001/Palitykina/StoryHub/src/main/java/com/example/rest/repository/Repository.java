package com.example.rest.repository;

import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public interface Repository<T>{
    Stream<T> getAll();
    Optional<T> get(long id);
    Optional<T> create(T input);
    Optional<T> update(T input);
    boolean delete(long id);
}
