package by.harlap.rest.util.dto.response;

import by.harlap.rest.dto.response.TweetResponseDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class TweetResponseDtoTestBuilder implements TestBuilder<TweetResponseDto> {

    private Long id = 1L;
    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);

    @Override
    public TweetResponseDto build() {
        TweetResponseDto tweet = new TweetResponseDto();

        tweet.setId(id);
        tweet.setAuthorId(authorId);
        tweet.setContent(content);
        tweet.setTitle(title);
        tweet.setCreated(created);
        tweet.setModified(modified);

        return tweet;
    }
}
