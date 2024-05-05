package by.harlap.jpa.util.dto.note.request;

import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CreateNoteDtoTestBuilder implements TestBuilder<CreateNoteDto> {

    private Long tweetId = 1L;
    private String content = "createdNote";

    @Override
    public CreateNoteDto build() {
        CreateNoteDto note = new CreateNoteDto();

        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}
