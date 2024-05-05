package by.bsuir.egor.storage;

import java.util.List;

public interface InMemoryRepository<T> {

    public T findById(long id);

    public List<T> findAll();

    public T deleteById(long id);

    public boolean deleteAll();

    public T insert(T insertObject);

    public boolean updateById(Long id, T updatingValue);

    public boolean update(T updatingValue);
}
