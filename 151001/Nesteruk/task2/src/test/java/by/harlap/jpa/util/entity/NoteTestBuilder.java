package by.harlap.jpa.util.entity;

import by.harlap.jpa.model.Note;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class NoteTestBuilder implements TestBuilder<Note> {

    private Long id = 1L;
    private Long tweetId = 1L;
    private String content = "dhjksc";
    private Tweet tweet = TweetTestBuilder.tweet().build();

    @Override
    public Note build() {
        Note note = new Note();

        note.setId(id);
        note.setContent(content);
        note.setTweet(tweet);

        return note;
    }
}
