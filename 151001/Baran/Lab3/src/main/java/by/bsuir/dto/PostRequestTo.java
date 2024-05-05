package by.bsuir.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 2048)
    private String content;
    private Long issueId;
}
