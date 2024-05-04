package com.example.publisherservice.dto.requestDto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestTo {

    private Long id;

    @Size(min = 2, max = 2048)
    private String content;

    private Long storyId;
}
