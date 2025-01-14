package by.bsuir.dc.impl.author.model;

import by.bsuir.dc.api.base.AbstractEntity;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Author extends AbstractEntity {
    @NonNull String login;
    @NonNull String password;
    @NonNull String firstname;
    @NonNull String lastname;
}
