package by.harlap.jpa.util.dto.tag.request;

import by.harlap.jpa.dto.request.UpdateTagDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class UpdateTagDtoTestBuilder implements TestBuilder<UpdateTagDto> {

    private Long id = 1L;
    private String name = "newTagName";

    @Override
    public UpdateTagDto build() {
        return new UpdateTagDto(id, name);
    }
}