package by.bsuir.publisher.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tbl_sticker")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Sticker extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "sticker")
    private List<StorySticker> storyStickers;
}
