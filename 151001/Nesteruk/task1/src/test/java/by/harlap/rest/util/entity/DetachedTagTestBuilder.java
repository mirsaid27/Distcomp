package by.harlap.rest.util.entity;

import by.harlap.rest.model.Tag;
import by.harlap.rest.util.TestBuilder;
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