package org.example.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDto {
    private int id;
    private int userId;
    @Length(min = 2, max = 64)
    private String title;
    @Length(min = 4, max = 2048)
    private String content;
    private Date created;
    private Date modified;
}
