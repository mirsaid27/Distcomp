package com.example.rest.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Story {
    private long id;
    private long userId;
    private String title;
    private String content;
    private OffsetDateTime created;
    private OffsetDateTime modified;
}
