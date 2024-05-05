package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос на создание автора")
public class CreatorRequestTo {

    @Schema(description = "Id автора", example = "2")
    int id;

    @Schema(description = "Логин автора", example = "Nikstanov")
    @Size(min = 2, max = 64, message = "Incorrect login size")
    String login;

    @Schema(description = "Пароль автора", example = "11223344")
    @Size(min = 8, max = 128, message = "Incorrect password size")
    String password;

    @Schema(description = "Имя автора", example = "Иван")
    @Size(min = 2, max = 64, message = "Incorrect firstname size")
    String firstname;

    @Schema(description = "Фамилия автора", example = "Иванов")
    @Size(min = 2, max = 64, message = "Incorrect lastname size")
    String lastname;
}
