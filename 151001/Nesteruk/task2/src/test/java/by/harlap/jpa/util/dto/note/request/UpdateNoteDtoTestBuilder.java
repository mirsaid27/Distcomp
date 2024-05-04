package by.harlap.jpa.util.dto.note.request;

import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class UpdateNoteDtoTestBuilder implements TestBuilder<UpdateNoteDto> {

    private Long id = 1L;
    private Long tweetId = 1L;
    private String content = "updatedContent";

    @Override
    public UpdateNoteDto build() {
        UpdateNoteDto note = new UpdateNoteDto();

        note.setId(id);
        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}
