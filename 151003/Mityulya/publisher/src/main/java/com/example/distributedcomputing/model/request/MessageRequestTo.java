package com.example.distributedcomputing.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageRequestTo {
        private Long id;

        @NotBlank
        @Size(min = 2, max = 2048)
        private String content;

        private Long tweetId;

        private String method;

        private String country;
}
