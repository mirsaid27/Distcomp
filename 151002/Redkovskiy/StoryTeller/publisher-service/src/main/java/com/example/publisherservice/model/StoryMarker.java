package com.example.publisherservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_story_marker")
public class StoryMarker {

    public StoryMarker(Story story, Marker marker) {
        this.story = story;
        this.marker = marker;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    private Marker marker;

}
