package by.bsuir.discussion.model.entity.interfaces;

import java.io.Serializable;

public interface EntityModel<T> extends Serializable {
    T getId();

    void setId(T id);
}
