package by.bsuir.distcomp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Identifiable {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

}
