package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос на создание твита")
public class TweetRequestTo {
    @Schema(description = "Id твита", example = "1")
    int id;

    @Schema(description = "Id автора", example = "2")
    @Min(value = 0, message = "creatorId must be >= 0")
    int creatorId;

    @Size(min = 2, max = 64, message = "Incorrect title size")
    @Schema(description = "Название твита", example = "Awesome tweet")
    String title;

    @Schema(description = "Содержание твита", example = "Awesome tweet body")
    @Size(min = 4, max = 2048, message = "Incorrect content size")
    String content;
}
