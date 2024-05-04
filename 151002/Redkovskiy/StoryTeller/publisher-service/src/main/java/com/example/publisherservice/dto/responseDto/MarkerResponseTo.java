package com.example.publisherservice.dto.responseDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkerResponseTo implements Serializable {

    private Long id;

    private String name;

    private Long storyId;
}
