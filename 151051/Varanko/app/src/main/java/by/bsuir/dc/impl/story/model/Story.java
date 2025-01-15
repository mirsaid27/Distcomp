package by.bsuir.dc.impl.story.model;

import by.bsuir.dc.api.base.AbstractEntity;
import by.bsuir.dc.impl.author.model.Author;
import by.bsuir.dc.impl.label.model.Label;
import by.bsuir.dc.impl.note.model.Note;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_story", schema = "distcomp")
@RequiredArgsConstructor
@NoArgsConstructor
public class Story extends AbstractEntity {
    @NonNull
    @Column(name = "author_id")
    long authorId;
    @Column(unique=true)
    @NonNull String title;
    @NonNull String content;
    LocalDateTime createdDatetime;
    LocalDateTime modifiedDatetime;

    @ManyToOne
    @JoinColumn(name="author_id", insertable = false, updatable = false)
    private Author author;
}
