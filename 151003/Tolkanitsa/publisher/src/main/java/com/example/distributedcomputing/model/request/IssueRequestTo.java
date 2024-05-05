package com.example.distributedcomputing.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IssueRequestTo(
        Long id,
        @NotBlank
        @Size(min = 2, max = 64)
        String title,
        @NotBlank
        @Size(min = 4, max = 2048)
        String content,
        Long creatorId
        ) {
}
