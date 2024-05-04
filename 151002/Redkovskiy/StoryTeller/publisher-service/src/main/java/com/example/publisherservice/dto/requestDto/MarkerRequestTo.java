package com.example.publisherservice.dto.requestDto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MarkerRequestTo {

    private Long id;

    @Size(min = 2, max = 32)
    private String name;

    private Long storyId;

}
