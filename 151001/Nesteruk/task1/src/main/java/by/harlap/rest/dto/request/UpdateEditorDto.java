package by.harlap.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEditorDto {

    @NotNull(message = "Id must not be empty")
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @Size(max = 64, message = "Login must not exceed 64 characters")
    @Size(min = 2, message = "Login must be at least 2 characters long")
    private String login;

    @Size(max = 128, message = "Password must not exceed 128 characters")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Size(max = 64, message = "Firstname must not exceed 64 characters")
    @Size(min = 2, message = "Firstname must be at least 2 characters long")
    private String firstname;

    @Size(max = 64, message = "Lastname must not exceed 64 characters")
    @Size(min = 2, message = "Lastname must be at least 2 characters long")
    private String lastname;
}
