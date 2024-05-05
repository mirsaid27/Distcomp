package by.harlap.jpa.util.entity;

import by.harlap.jpa.model.Tag;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class TagTestBuilder implements TestBuilder<Tag> {

    private Long id = 1L;
    private String name = "tagName";

    @Override
    public Tag build() {
        Tag tag = new Tag();

        tag.setId(id);
        tag.setName(name);

        return tag;
    }
}
