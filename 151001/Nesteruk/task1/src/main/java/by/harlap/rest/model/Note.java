package by.harlap.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note extends AbstractEntity {

    private Long tweetId;
    private String content;
}
