package com.example.rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "tbl_story")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String title;
    private String content;
    private OffsetDateTime created;
    private OffsetDateTime modified;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "story_label",
            joinColumns = @JoinColumn(name = "storyId"),
            inverseJoinColumns = @JoinColumn(name = "labelId")
    )
    private final List<Label> labels = new ArrayList<>();
}
