package by.bsuir.poit.dc.kafka.dto;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Builder
public record KafkaUpdateCommentDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    @NotNull(groups = Create.class)
    Long tweetId,
    @Size(min = 1)
    //languages should be parsed in order of popularity and interest
    List<String> countries,
    String  content
) {
}
