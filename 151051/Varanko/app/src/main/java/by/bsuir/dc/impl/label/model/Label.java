package by.bsuir.dc.impl.label.model;

import by.bsuir.dc.api.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_label", schema = "distcomp")
@RequiredArgsConstructor
public class Label extends AbstractEntity {
    @Column(unique=true)
    @NotNull String name;
}
