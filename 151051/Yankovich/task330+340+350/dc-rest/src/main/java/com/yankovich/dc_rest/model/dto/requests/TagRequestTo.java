package com.yankovich.dc_rest.model.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagRequestTo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
}
