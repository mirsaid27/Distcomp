package com.rmakovetskij.dc_rest.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponseTo  implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Long editorId;
}
