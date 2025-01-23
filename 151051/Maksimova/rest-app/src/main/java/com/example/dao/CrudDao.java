package com.example.dao;

import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

public interface CrudDao <T>{
    T save(T entity);
    void delete(long id) throws DeleteException;
    List<T> findAll();
    Optional<T> findById(long id);
    T update(T entity) throws UpdateException;
}
