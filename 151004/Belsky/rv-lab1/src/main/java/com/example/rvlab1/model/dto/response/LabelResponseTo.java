package com.example.rvlab1.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LabelResponseTo {
    private Long id;
    private String name;
    private Long issueId;
}
