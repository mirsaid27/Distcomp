package com.padabied.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseTo implements Serializable {
    private Long id;
    private String content;
    private Long storyId;
    private String country = "Belarus";
}
