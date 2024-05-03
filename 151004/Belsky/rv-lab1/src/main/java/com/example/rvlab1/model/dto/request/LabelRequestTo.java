package com.example.rvlab1.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LabelRequestTo {
    private String name;
    private Long issueId;
}
