package by.harlap.jpa.util.dto.tag.response;

import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class CreateTagResponseTestBuilder implements TestBuilder<TagResponseDto> {

    private Long id = 2L;
    private String name = "name";

    @Override
    public TagResponseDto build() {
        TagResponseDto tag = new TagResponseDto();

        tag.setId(id);
        tag.setName(name);

        return tag;
    }
}