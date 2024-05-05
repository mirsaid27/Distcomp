package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.UpdateNoteDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class UpdateNoteDtoTestBuilder implements TestBuilder<UpdateNoteDto> {

    private Long id = 1L;
    private Long tweetId = 1L;
    private String content = "dhjkssff";

    @Override
    public UpdateNoteDto build() {
        UpdateNoteDto note = new UpdateNoteDto();

        note.setId(id);
        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}
