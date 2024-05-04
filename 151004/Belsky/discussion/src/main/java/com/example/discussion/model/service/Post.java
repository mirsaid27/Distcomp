package com.example.discussion.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Post {
    private Long id;
    private Long issueId;
    private String content;
}
