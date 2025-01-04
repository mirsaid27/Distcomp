package com.example.domain;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private Long issueId;
}
