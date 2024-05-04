package by.bsuir.poit.dc.rest.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import jakarta.validation.constraints.NotNull;

/**
 * @author Name Surname
 * 
 */
public record UpdateTweetStickerDto(
    @NotNull(groups = Create.class)
    Long stickerId
) {
}
