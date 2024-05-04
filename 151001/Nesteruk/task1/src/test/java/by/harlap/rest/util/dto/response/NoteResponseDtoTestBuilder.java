package by.harlap.rest.util.dto.response;

import by.harlap.rest.dto.response.NoteResponseDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class NoteResponseDtoTestBuilder implements TestBuilder<NoteResponseDto> {

    private Long id = 1L;
    private Long tweetId = 1L;
    private String content = "dhjksc";

    @Override
    public NoteResponseDto build() {
        NoteResponseDto note = new NoteResponseDto();

        note.setId(id);
        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}