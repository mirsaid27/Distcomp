package by.bsuir.test_rw.repository.interfaces;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<I, E extends EntityModel<I>> {
    Optional<E> findById(I id);

    List<E> findAll();

    E save(E entity);

    void deleteById(I id);
}
