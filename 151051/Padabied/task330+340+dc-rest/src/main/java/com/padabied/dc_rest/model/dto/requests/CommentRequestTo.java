package com.padabied.dc_rest.model.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestTo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 2048)
    private String content;

    @NotNull
    private Long storyId;

    private String country = "Belarus";
}