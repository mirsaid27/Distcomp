package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;


public record StoryRequestTo(Long id,
                             Long creatorId,
                             @Size(min = 2, max = 64)
                             String title,
                             @Size(min = 4, max = 2048)
                             String content){
}
