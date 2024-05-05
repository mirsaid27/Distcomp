package com.example.discussion.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 2048)
    private String content;
    private Long issueId;
    private String method;
    private String country;
}
