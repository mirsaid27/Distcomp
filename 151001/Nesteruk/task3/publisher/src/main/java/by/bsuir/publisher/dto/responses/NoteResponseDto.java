package by.bsuir.publisher.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class NoteResponseDto {
    private Long id;
    private Long tweetId;
    private String content;
}
