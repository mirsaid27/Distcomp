package ru.bsuir.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

public record LabelRequestTo (Long id,
                              @Size(min = 2, max = 32)
                              String name) {
}
