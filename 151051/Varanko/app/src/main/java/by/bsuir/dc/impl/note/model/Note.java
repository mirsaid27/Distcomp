package by.bsuir.dc.impl.note.model;

import by.bsuir.dc.api.base.AbstractEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Note extends AbstractEntity {
    @NotNull long storyId;
    @NotNull String content;
}
