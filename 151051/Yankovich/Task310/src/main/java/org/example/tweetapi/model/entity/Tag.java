package org.example.tweetapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    @NotNull(message = "Tag name cannot be null")
    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters")
    private String name;
}
