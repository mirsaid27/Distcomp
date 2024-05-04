package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.model.Editor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Editor}
 */
public record UpdateEditorDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String login,
    @Size(min = 8, max = 128)
    @NotNull(groups = Create.class)
    String password,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String firstname,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String lastname
) implements Serializable {
}