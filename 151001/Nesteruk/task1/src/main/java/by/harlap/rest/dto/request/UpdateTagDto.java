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
public class UpdateTagDto {

    @NotNull(message = "Id must not be empty")
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @Size(max = 32, message = "Name must not exceed 32 characters")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
}
