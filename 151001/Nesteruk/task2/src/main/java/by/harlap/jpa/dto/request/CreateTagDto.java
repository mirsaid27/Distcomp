package by.harlap.jpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagDto {

    @NotBlank(message = "Name must not be empty")
    @Size(max = 32, message = "Name must not exceed 32 characters")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
}
