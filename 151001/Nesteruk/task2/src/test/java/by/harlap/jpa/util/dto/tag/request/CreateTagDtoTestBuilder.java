package by.harlap.jpa.util.dto.tag.request;

import by.harlap.jpa.dto.request.CreateTagDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tag")
@With
public class CreateTagDtoTestBuilder implements TestBuilder<CreateTagDto> {

    private String name = "name";

    @Override
    public CreateTagDto build() {
        return new CreateTagDto(name);
    }
}
