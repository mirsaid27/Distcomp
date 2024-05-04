package by.harlap.jpa.util.dto.editor.request;

import by.harlap.jpa.dto.request.CreateEditorDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class CreateEditorDtoTestBuilder implements TestBuilder<CreateEditorDto> {

    private String login = "login";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public CreateEditorDto build() {
        return new CreateEditorDto(login, password, firstname, lastname);
    }
}
