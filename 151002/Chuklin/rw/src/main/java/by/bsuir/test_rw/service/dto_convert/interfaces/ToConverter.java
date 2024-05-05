package by.bsuir.test_rw.service.dto_convert.interfaces;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.model.entity.interfaces.EntityModel;

public interface ToConverter<E extends EntityModel<?>, A, B> {
    E convertToEntity(A requestTo) throws ToConvertException;

    B convertToDto(E entity) throws ToConvertException;
}
