package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Ответ c автором")
public class CreatorResponseTo {
    @Schema(description = "Id автора", example = "2")
    int id;
    @Schema(description = "Логин автора", example = "Nikstanov")
    String login;
    @Schema(description = "Пароль автора", example = "11223344")
    String password;
    @Schema(description = "Имя автора", example = "Иван")
    String firstname;
    @Schema(description = "Фамилия автора", example = "Иванов")
    String lastname;
}
