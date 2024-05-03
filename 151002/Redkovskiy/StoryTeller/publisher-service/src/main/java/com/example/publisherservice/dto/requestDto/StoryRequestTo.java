package com.example.publisherservice.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StoryRequestTo {

    private Long id;

    @NotNull
    @Size(min = 2, max = 64)
    private String title;

    @Size(min = 4, max = 2048)
    private String content;

    @NotNull
    private Long creatorId;

}
