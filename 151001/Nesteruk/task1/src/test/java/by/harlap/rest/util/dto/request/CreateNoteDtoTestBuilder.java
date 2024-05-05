package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.CreateNoteDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CreateNoteDtoTestBuilder implements TestBuilder<CreateNoteDto> {

    private Long tweetId = 1L;
    private String content = "dhjksc";

    @Override
    public CreateNoteDto build() {
        CreateNoteDto note = new CreateNoteDto();

        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}
