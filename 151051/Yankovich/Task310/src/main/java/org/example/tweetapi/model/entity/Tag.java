package org.example.tweetapi.model.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;   //поменял с javax на jakarta

@Data
@Entity
@Table(name = "tbl_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 32)
    @Column(nullable = false, unique = true)
    private String name;
}