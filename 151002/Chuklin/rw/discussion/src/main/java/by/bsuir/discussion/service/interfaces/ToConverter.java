package by.bsuir.discussion.service.interfaces;

import by.bsuir.discussion.exception.model.dto_convert.ToConvertException;
import by.bsuir.discussion.model.entity.interfaces.EntityModel;

public interface ToConverter<E extends EntityModel<?>, A, B> {
    E convertToEntity(A requestTo) throws ToConvertException;

    B convertToDto(E entity) throws ToConvertException;
}
