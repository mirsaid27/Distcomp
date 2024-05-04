package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.UpdateTagDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class UpdateTagDtoTestBuilder implements TestBuilder<UpdateTagDto> {

    private Long id = 1L;
    private String name = "tagName";

    @Override
    public UpdateTagDto build() {
        return new UpdateTagDto(id, name);
    }
}