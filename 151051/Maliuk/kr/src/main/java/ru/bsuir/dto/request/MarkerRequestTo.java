package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;

public record MarkerRequestTo(Long id,
                              @Size(min = 2, max = 32)
                              String name) {
}
