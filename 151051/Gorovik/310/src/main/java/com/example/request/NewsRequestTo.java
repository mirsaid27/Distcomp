package com.example.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NewsRequestTo(
        Long id,
        Long creatorId,
        @NotBlank
        @Length(min = 2, max = 64)
        String title,
        @NotBlank
        @Length(min = 4, max = 2048)
        String content
) {
}