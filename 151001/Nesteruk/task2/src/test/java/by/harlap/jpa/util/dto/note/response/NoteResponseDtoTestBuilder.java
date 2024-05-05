package by.harlap.jpa.util.dto.note.response;

import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class NoteResponseDtoTestBuilder implements TestBuilder<NoteResponseDto> {

    private Long id = 1L;
    private Long tweetId = 1L;
    private String content = "content";

    @Override
    public NoteResponseDto build() {
        NoteResponseDto note = new NoteResponseDto();

        note.setId(id);
        note.setContent(content);
        note.setTweetId(tweetId);

        return note;
    }
}