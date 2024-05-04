package by.harlap.jpa.util.entity;

import by.harlap.jpa.model.Editor;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class TweetTestBuilder implements TestBuilder<Tweet> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);
    private Editor editor = EditorTestBuilder.editor().build();

    @Override
    public Tweet build() {
        Tweet tweet = new Tweet();

        tweet.setId(id);
        tweet.setEditor(editor);
        tweet.setContent(content);
        tweet.setTitle(title);
        tweet.setCreated(created);
        tweet.setModified(modified);

        return tweet;
    }
}
