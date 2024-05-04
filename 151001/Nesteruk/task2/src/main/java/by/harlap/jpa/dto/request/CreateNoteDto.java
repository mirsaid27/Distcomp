package by.harlap.jpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDto {

    @PositiveOrZero(message = "tweetId must be positive or zero")
    private Long tweetId;

    @NotBlank(message = "Content must not be empty")
    @Size(max = 2048, message = "Content must not exceed 2048 characters")
    @Size(min = 2, message = "Content must be at least 2 characters long")
    private String content;
}
