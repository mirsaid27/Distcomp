package com.example.publisherservice.dto.responseDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostResponseTo implements Serializable {

    private Long id;

    private String content;

    private Long storyId;

}
