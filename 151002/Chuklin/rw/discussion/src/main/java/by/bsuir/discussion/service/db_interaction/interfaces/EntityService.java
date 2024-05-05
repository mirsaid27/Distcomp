package by.bsuir.discussion.service.db_interaction.interfaces;

import by.bsuir.discussion.exception.model.not_found.EntityNotFoundException;
import by.bsuir.discussion.model.entity.interfaces.EntityModel;

import java.util.List;

public interface EntityService<I, E extends EntityModel<I>> {
    E findById(I id) throws EntityNotFoundException;

    List<E> findAll();

    void save(E entity);

    void deleteById(I id) throws EntityNotFoundException;

    void update(E entity);
}
