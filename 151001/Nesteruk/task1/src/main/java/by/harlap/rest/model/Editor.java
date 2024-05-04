package by.harlap.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Editor extends AbstractEntity {

    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
