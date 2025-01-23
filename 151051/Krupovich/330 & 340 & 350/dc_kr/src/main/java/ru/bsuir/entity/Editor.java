package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "tbl_editor")
public class Editor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false, unique = true, length = 64)
    @Size(min = 2, max = 64)
    private String login;

    @Column(nullable = false)
    @Size(min = 8, max = 128)
    private String password;

    @Column(nullable = false)
    @Size(min = 2, max = 64)
    private String firstname;

    @Column(nullable = false)
    @Size(min = 2, max = 64)
    private String lastname;

}
