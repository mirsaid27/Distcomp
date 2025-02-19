package com.example.rest.dto.requestDto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LabelRequestTo {
    private long id;

    @Size(min = 2, max = 32)
    private String name;
}
