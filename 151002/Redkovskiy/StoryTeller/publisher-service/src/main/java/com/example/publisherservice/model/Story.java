package com.example.publisherservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tbl_story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String title;

    @Column(length = 2048)
    private String content;

    private LocalDateTime created;

    private LocalDateTime modified;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Creator creator;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "story")
    private Set<StoryMarker> markers = new HashSet<>();

}
