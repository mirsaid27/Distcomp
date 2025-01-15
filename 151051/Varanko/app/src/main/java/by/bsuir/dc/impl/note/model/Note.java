package by.bsuir.dc.impl.note.model;

import by.bsuir.dc.api.base.AbstractEntity;
import by.bsuir.dc.impl.story.model.Story;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_note", schema = "distcomp")
@RequiredArgsConstructor
public class Note extends AbstractEntity {
    @NotNull
    @Column(name = "story_id")
    long storyId;
    @NotNull String content;

    @ManyToOne
    @JoinColumn(name = "story_id",insertable=false, updatable=false)
    private Story story;
}
