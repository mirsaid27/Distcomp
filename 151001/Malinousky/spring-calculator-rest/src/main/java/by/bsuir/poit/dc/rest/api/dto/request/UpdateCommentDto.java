package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record UpdateCommentDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @NotNull(groups = Create.class)
    Long tweetId,
    @Size(min = 2, max = 2048)
    @NotNull(groups = Create.class)
    String content
) implements Serializable {
}