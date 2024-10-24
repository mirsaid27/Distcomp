package com.padabied.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseTo {
    private Long id;
    private String content;
    private Long storyId;
}
