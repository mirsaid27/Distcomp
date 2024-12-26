package org.example.tweetapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    @NotNull(message = "Tweet ID cannot be null")
    private Long tweetid;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 2048, message = "Content cannot exceed 500 characters")
    private String content;
}
