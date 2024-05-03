package com.example.publisherservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048)
    private String content;

    @ManyToOne()
    @JoinColumn(name = "story_id", referencedColumnName = "id")
    private Story story;
}
