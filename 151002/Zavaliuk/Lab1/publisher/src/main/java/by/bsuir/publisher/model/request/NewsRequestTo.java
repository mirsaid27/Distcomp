package by.bsuir.publisher.model.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NewsRequestTo(
        Long id,
        Long editorId,

        @NotBlank
        @Length(min =  2, max = 64)
        String title,
        
        @Length(min =  4, max = 2048)
        String content
) { }
