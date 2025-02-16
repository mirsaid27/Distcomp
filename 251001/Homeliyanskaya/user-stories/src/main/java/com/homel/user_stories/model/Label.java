package com.homel.user_stories.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    private Long id;

    private String name;

    private Set<Story> stories = new HashSet<>();
}
