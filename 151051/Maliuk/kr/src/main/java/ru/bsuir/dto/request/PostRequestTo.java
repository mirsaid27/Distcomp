package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;


public record PostRequestTo(Long id,
                            Long storyId,
                            @Size(min = 4, max = 2048)
                               String content) {
}
