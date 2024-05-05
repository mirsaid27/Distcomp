package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос на создание коммента")
public class CommentRequestTo {
    int id;

    @NotNull
    @Schema(description = "айди твита", example = "1")
    @Min(value = 0)
    Integer tweetId;

    @NotNull
    @Schema(description = "Содержание коммента", example = "Сообщение")
    @Size(min = 2, max = 2048, message = "Incorrect content size")
    String content;
}
