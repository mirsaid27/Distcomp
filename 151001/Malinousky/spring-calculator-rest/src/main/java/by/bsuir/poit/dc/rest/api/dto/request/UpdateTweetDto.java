package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.model.Tweet;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link Tweet}
 */
@Builder
public record UpdateTweetDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @Size(min = 2, max = 64)
    @NotNull(groups = Create.class)
    String title,

    @Size(min = 4, max = 2048)
    @NotNull(groups = Create.class)
    String content,
    @NotNull(groups = Create.class)
    Long editorId

) implements Serializable {
}