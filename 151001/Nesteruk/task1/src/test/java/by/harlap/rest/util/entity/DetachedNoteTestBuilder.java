package by.harlap.rest.util.entity;

import by.harlap.rest.model.Note;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class DetachedNoteTestBuilder implements TestBuilder<Note> {

    private Long tweetId = 1L;
    private String content = "dhjksc";

    @Override
    public Note build() {
        Note note = new Note();

        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}