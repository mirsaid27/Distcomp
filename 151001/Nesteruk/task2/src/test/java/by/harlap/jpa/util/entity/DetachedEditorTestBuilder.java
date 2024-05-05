package by.harlap.jpa.util.entity;

import by.harlap.jpa.model.Editor;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class DetachedEditorTestBuilder implements TestBuilder<Editor> {

    private String login = "editor2456";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public Editor build() {
        Editor editor = new Editor();

        editor.setLogin(login);
        editor.setFirstname(firstname);
        editor.setPassword(password);
        editor.setLastname(lastname);

        return editor;
    }
}
