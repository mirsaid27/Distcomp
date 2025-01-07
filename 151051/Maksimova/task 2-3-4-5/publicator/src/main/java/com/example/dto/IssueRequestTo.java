package com.example.dto;

import com.example.utils.StringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 64)
    private String title;
    @JsonDeserialize(using = StringDeserializer.class)
    private String content;
    private Long editorId;
    private Long stickerId;
}
