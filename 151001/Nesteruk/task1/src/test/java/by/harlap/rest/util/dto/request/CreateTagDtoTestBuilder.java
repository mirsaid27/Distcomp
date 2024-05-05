package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.CreateTagDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class CreateTagDtoTestBuilder implements TestBuilder<CreateTagDto> {

    private String name = "tagName";

    @Override
    public CreateTagDto build() {
        return new CreateTagDto(name);
    }
}
