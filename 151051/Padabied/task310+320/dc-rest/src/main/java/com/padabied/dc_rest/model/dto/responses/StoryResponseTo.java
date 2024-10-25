package com.padabied.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponseTo {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
