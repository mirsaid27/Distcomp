package by.bsuir.test_rw.service.db_interaction.interfaces;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.entity.interfaces.EntityModel;

import java.util.List;

public interface EntityService<I, E extends EntityModel<I>> {
    E findById(I id) throws EntityNotFoundException;

    List<E> findAll();

    void save(E entity);

    void deleteById(I id) throws EntityNotFoundException;

    E update(E entity);
}
