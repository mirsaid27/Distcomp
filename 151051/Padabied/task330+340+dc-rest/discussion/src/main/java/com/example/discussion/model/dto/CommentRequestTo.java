package com.example.discussion.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestTo implements Serializable {

    private Long id;

    @NotBlank(message = "Country is mandatory")
    private String country = "BY";

    @NotNull(message = "Story ID is mandatory")
    private Long storyId;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 2, max = 2048, message = "Content length must be between 2 and 2048 characters")
    private String content;
}
