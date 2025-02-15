package by.kopvzakone.distcomp.repositories;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.stream.Stream;

public interface Repo<T> extends Repository<T, Long> {
    Stream<T> getAll();
    public Optional<T> get(long id);
    public Optional<T> create(T input);
    public Optional<T> update(T input);
    public boolean delete(long id);
}
