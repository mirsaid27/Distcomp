package by.bsuir.poit.dc.cassandra.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * @author Name Surname
 * 
 */
@Builder(toBuilder = true)
public record UpdateCommentDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    Long tweetId,
    @Size(min = 2, max = 2048)
    @NotNull(groups = Create.class)
    String content,
    @Null
    Short status
) {

}
