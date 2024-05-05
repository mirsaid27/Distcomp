package org.education.bean.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос на создание маркера")
public class MarkerRequestTo {
    @Schema(description = "Id маркера", example = "1")
    int id;

    @Size(min = 2, max = 32, message = "Incorrect name size")
    @Schema(description = "Имя маркера", example = "Маркер1")
    String name;
}
