package com.example.rvlab1.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Label {
    private Long id;
    private String name;
    private Set<Issue> issues = new HashSet<>();
}
