package by.harlap.jpa.util.dto.editor.request;

import by.harlap.jpa.dto.request.UpdateEditorDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class UpdateEditorDtoTestBuilder implements TestBuilder<UpdateEditorDto> {

    private Long id = 1L;
    private String login = "editor";
    private String password = "fdscvgrfds";
    private String firstname = "first";
    private String lastname = "last";

    @Override
    public UpdateEditorDto build() {
        return new UpdateEditorDto(id, login, password, firstname, lastname);
    }
}