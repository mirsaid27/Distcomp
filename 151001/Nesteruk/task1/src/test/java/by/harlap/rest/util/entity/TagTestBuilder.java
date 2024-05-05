package by.harlap.rest.util.entity;

import by.harlap.rest.model.Tag;
import by.harlap.rest.util.TestBuilder;
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
