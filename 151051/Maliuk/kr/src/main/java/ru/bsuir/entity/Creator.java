package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bsuir.abstractions.AbstractEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_creator")
public class Creator extends AbstractEntity {

    @Column(nullable = false, unique = true, length = 64)
    @Size(min = 2, max = 64)
    private String login;

    @Column(nullable = false)
    @Size(min = 8, max = 128)
    private String password;

    @Size(min = 2, max = 64)
    private String firstname;

    @Size(min = 2, max = 64)
    private String lastname;

}
