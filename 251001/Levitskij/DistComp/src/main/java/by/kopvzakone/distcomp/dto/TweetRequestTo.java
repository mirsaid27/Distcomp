package by.kopvzakone.distcomp.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TweetRequestTo {
    long id;
    long editorId;
    @Size(min = 2, max = 64)
    String title;
    @Size(min = 4, max = 2048)
    String content;
}
