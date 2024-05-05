package by.bsuir.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
    private Long tweetId;
}
