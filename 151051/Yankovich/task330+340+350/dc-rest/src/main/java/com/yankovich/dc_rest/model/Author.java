package com.yankovich.dc_rest.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_author")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 64)
    @Column(unique = true, nullable = false)
    private String login;

    @Size(min = 8, max = 128)
    @Column(nullable = false)
    private String password;

    @Size(min = 2, max = 64)
    @Column(nullable = false)
    private String firstname;

    @Size(min = 2, max = 64)
    @Column(nullable = false)
    private String lastname;
}
