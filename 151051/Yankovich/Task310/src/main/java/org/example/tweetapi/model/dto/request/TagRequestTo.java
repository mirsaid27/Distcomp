package org.example.tweetapi.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TagRequestTo {

    @NotNull(message = "Tag name cannot be null")
    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters")
    private String name;
}
