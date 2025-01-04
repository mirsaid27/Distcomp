package com.example.domain;

import lombok.Data;

@Data
public class Issue {
    private Long id;
    private String name;
    private Long editorId;
}
