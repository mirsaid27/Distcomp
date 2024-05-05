package by.bsuir.test_rw.model.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequestTO {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
}

