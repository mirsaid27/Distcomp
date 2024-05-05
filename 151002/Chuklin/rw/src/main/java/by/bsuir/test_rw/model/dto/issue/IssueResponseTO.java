package by.bsuir.test_rw.model.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponseTO {
    private String title;
    private String content;
    private Long id;
    private Long creatorId;
    private Date created;
    private Date modified;
}
