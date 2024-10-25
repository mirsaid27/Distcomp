package com.padabied.dc_rest.model.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StoryRequestTo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 64)
    private String title;

    @NotBlank
    @Size(min = 4, max = 2048)
    private String content;

    private Long userId; // Идентификатор пользователя, который добавляет историю
}
