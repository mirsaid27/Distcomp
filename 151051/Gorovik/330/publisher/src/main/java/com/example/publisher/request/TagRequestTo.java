package com.example.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record TagRequestTo(
        Long id,
        @NotBlank
        @Length(min = 2, max = 32)
        String name
) {
}