package com.padabied.dc_rest.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface Repository <T, ID> {
    List<T> findAll();            // Получить все записи
    Optional<T> findById(ID id);   // Найти запись по ID
    T save(T entity);              // Сохранить сущность
    void deleteById(ID id);        // Удалить сущность по ID
}
