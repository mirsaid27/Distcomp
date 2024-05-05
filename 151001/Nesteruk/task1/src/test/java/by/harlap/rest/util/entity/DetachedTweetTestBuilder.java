package by.harlap.rest.util.entity;

import by.harlap.rest.model.Tweet;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class DetachedTweetTestBuilder implements TestBuilder<Tweet> {

    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);

    @Override
    public Tweet build() {
        Tweet tweet = new Tweet();

        tweet.setAuthorId(authorId);
        tweet.setContent(content);
        tweet.setTitle(title);
        tweet.setCreated(created);
        tweet.setModified(modified);

        return tweet;
    }
}
