package by.bsuir.dc.impl.story.model;

import by.bsuir.dc.api.base.AbstractEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class Story extends AbstractEntity {
    @NonNull long authorId;
    @NonNull String title;
    @NonNull String content;
    LocalDateTime createdDatetime;
    LocalDateTime modifiedDatetime;
}
