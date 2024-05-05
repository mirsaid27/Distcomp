package by.bsuir.discussion.model.entity.implementations;

import lombok.Data;
import java.util.Date;

@Data
public class Issue {
    private Long id;
    private String title;
    private String content;
    private Date creationDate;
    private Date updateDate;
}
