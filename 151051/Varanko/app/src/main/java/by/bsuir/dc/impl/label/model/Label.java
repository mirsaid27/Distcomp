package by.bsuir.dc.impl.label.model;

import by.bsuir.dc.api.base.AbstractEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@Builder
@RequiredArgsConstructor
public class Label extends AbstractEntity {
    @NotNull String name;
}
