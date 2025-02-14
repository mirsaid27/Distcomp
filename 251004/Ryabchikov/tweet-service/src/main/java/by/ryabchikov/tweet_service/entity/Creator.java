package by.ryabchikov.tweet_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Creator {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
