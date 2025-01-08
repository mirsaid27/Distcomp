package org.example.tweetapi.model.dto.request;

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
    private Long tweetId;
}
