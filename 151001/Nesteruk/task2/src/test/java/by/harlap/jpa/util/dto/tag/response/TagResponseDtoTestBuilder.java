package by.harlap.jpa.util.dto.tag.response;

import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class TagResponseDtoTestBuilder implements TestBuilder<TagResponseDto> {

    private Long id = 1L;
    private String name = "tagName";

    @Override
    public TagResponseDto build() {
        TagResponseDto tag = new TagResponseDto();

        tag.setId(id);
        tag.setName(name);

        return tag;
    }
}
