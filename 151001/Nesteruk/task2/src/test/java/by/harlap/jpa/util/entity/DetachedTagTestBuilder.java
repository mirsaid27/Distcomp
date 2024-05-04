package by.harlap.jpa.util.entity;

import by.harlap.jpa.model.Tag;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class DetachedTagTestBuilder implements TestBuilder<Tag> {

    private String name = "tagName";

    @Override
    public Tag build() {
        Tag tag = new Tag();

        tag.setName(name);

        return tag;
    }
}