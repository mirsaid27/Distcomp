package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.bsuir.abstractions.AbstractEntity;

@Entity
@Data
@Table(name = "tbl_marker")
public class Marker extends AbstractEntity {

    @Column( nullable = false, length = 32)
    @Size(min = 2, max = 32)
    private String name;
}
