package by.harlap.jpa.util.dto.tweet.response;

import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class TweetResponseDtoTestBuilder implements TestBuilder<TweetResponseDto> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.parse("2024-03-18T00:50:05.464483");
    private LocalDateTime modified = LocalDateTime.parse("2024-03-18T00:50:05.464483");

    @Override
    public TweetResponseDto build() {
        TweetResponseDto tweet = new TweetResponseDto();

        tweet.setId(id);
        tweet.setEditorId(editorId);
        tweet.setContent(content);
        tweet.setTitle(title);
        tweet.setCreated(created);
        tweet.setModified(modified);

        return tweet;
    }
}
