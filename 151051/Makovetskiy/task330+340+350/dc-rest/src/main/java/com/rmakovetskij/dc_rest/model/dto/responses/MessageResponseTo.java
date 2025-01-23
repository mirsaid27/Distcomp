package com.rmakovetskij.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseTo implements Serializable {
    private Long id;
    private String content;
    private Long issueId;
    private String country = "Belarus";
}
