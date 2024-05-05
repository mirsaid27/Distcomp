package by.bsuir.publisher.model.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record StickerRequestTo(
        Long id,
        @NotBlank
        @Length(min = 2, max = 32)
        String name) {
}
