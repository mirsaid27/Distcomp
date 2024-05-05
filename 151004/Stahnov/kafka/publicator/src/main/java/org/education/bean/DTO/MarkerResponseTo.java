package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Ответ c маркером")
public class MarkerResponseTo {
    @Schema(description = "Id маркера", example = "1")
    int id;
    @Schema(description = "Имя маркера", example = "Маркер1")
    String name;
}