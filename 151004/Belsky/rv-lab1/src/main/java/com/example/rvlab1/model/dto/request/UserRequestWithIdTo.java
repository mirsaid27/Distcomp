package com.example.rvlab1.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserRequestWithIdTo extends UserRequestTo {
    private Long id;
}
