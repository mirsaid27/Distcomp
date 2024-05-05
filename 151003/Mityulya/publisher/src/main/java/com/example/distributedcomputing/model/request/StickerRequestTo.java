package com.example.distributedcomputing.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StickerRequestTo(
        Long id,
        @NotBlank
        @Size(min = 4, max = 32)
        String name
) {
}
