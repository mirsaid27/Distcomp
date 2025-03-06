package by.bsuir.distcomp.repository;

import java.util.List;

public interface IRepository<T> {
    T findById(Long id);
    List<T> findAll();
    T create(T author);
    T update(T author);
    void deleteById(Long id);
}
