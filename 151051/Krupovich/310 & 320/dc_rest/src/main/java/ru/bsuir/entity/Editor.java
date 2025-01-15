package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.bsuir.abstractions.AbstractEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_editor")
public class Editor extends AbstractEntity {

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
