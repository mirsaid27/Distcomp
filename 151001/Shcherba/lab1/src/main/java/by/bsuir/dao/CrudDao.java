package by.bsuir.dao;

import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

public interface CrudDao <T>{
    T save(T entity);
    void delete(long id) throws DeleteException;
    List<T> findAll();
    Optional<T> findById(long id);
    T update(T entity) throws UpdateException;
}
