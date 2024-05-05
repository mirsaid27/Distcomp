package com.example.publisherservice.dto.responseDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoryResponseTo implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String created;

    private String modified;

    private Long creatorId;
}
