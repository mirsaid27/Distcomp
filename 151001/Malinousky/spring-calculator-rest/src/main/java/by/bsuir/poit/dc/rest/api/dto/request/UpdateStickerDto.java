package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.model.Sticker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Sticker}
 */
public record UpdateStickerDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @Size(min = 2, max = 34)
    @NotNull(groups = Create.class)
    String name
) implements Serializable {
}