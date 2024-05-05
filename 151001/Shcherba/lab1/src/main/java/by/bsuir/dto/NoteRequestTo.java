package by.bsuir.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 4096)
    private String content;
    private Long tweetId;
}
