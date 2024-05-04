package by.harlap.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTweetDto {

    @NotNull(message = "Id must not be empty")
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @PositiveOrZero(message = "editorId must be positive or zero")
    private Long editorId;

    @Size(max = 64, message = "Title must not exceed 64 characters")
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;

    @Size(max = 2048, message = "Content must not exceed 2048 characters")
    @Size(min = 4, message = "Content must be at least 4 characters long")
    private String content;
}
