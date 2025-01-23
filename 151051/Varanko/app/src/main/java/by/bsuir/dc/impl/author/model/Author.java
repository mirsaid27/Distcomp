package by.bsuir.dc.impl.author.model;

import by.bsuir.dc.api.base.AbstractEntity;
import by.bsuir.dc.impl.story.model.Story;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_author", schema = "distcomp")
@RequiredArgsConstructor
@NoArgsConstructor
public class Author extends AbstractEntity {
    @Column(unique=true)
    @NonNull String login;
    @NonNull String password;
    @NonNull String firstname;
    @NonNull String lastname;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();
}
