package com.padabied.dc_rest.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_story")
public class Story implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 64)
    @Column(unique = true, nullable = false)
    private String title;

    @Size(min = 4, max = 2048)
    @Column(nullable = false)
    private String content;


    @CreationTimestamp
    private LocalDateTime created;


    @UpdateTimestamp
    private LocalDateTime modified;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}