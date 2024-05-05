package com.example.rvlab1.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class Issue {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private OffsetDateTime created;
    private OffsetDateTime modified;
}
