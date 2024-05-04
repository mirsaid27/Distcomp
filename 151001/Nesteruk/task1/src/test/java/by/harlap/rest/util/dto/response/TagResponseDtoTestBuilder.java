package by.harlap.rest.util.dto.response;

import by.harlap.rest.dto.response.TagResponseDto;
import by.harlap.rest.util.TestBuilder;
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
