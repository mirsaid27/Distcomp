package by.harlap.jpa.util.dto.editor.response;

import by.harlap.jpa.dto.response.EditorResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class CreateEditorResponseDtoTestBuilder implements TestBuilder<EditorResponseDto> {

    private Long id = 2L;
    private String login = "login";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public EditorResponseDto build() {
        return new EditorResponseDto(id, login, password, firstname, lastname);
    }
}
