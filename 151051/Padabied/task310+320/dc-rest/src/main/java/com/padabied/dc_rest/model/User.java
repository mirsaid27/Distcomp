package com.padabied.dc_rest.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "tbl_user")
public class User {
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
