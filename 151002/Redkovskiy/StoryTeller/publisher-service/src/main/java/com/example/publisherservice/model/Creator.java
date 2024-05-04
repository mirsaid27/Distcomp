package com.example.publisherservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tbl_creator")
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String login;

    @Column(length = 128)
    private String password;

    @Column(length = 64)
    private String firstName;

    @Column(length = 64)
    private String lastName;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    Set<Story> stories = new HashSet<>();
}
