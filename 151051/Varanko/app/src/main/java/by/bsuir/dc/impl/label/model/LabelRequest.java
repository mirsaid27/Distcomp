package by.bsuir.dc.impl.label.model;

import jakarta.validation.constraints.Size;

public record LabelRequest(
        long id,
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters")
        String name
){}
