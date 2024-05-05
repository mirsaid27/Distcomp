package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Ответ c комментами")
public class CommentResponseTo {
    @Schema(description = "Id коммента", example = "1")
    int id;
    @Schema(description = "Id твита", example = "2")
    int tweetId;
    @Schema(description = "Содержание коммента", example = "Сообщение")
    String content;
}
