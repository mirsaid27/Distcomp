package com.homel.user_stories.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    private Long id;

    private User user;

    private String title;

    private String content;

    private LocalDate created;

    private LocalDate modified;

    private List<Notice> notices;

    private Set<Label> labels = new HashSet<>();
}
