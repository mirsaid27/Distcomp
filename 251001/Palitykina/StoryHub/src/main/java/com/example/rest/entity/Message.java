package com.example.rest.entity;

import lombok.Data;

@Data
public class Message {
    private long id;
    private long storyId;
    private String content;
}
