package com.example.rest.dto.requestDto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StoryRequestTo {
    private long id;

    private long userId;

    @Size(min = 2, max = 64)
    private String title;

    @Size(min = 4, max = 2048)
    private String content;
}
