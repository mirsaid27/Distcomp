package by.harlap.rest.util.dto.response;

import by.harlap.rest.dto.response.AuthorResponseDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "author")
@With
public class AuthorResponseDtoTestBuilder implements TestBuilder<AuthorResponseDto> {

    private Long id = 1L;
    private String login = "author2456";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public AuthorResponseDto build() {
        return new AuthorResponseDto(id, login, password, firstname, lastname);
    }
}
