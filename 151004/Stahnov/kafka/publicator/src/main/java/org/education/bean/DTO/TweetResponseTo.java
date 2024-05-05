package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Ответ c твитом")
public class TweetResponseTo {
    @Schema(description = "Id твита", example = "1")
    int id;

    @Schema(description = "Id автора", example = "2")
    int creatorId;
    @Schema(description = "Название твита", example = "Awesome tweet")
    String title;
    @Schema(description = "Содержание твита", example = "Awesome tweet body")
    String content;
}
