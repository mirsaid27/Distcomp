package com.example.rvlab1.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Post {
    private Long id;
    private Long issueId;
    private String content;
}
