package by.harlap.jpa.util.dto.tweet.request;

import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class UpdateTweetDtoTestBuilder implements TestBuilder<UpdateTweetDto> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public UpdateTweetDto build() {
        UpdateTweetDto tweet = new UpdateTweetDto();

        tweet.setId(id);
        tweet.setEditorId(editorId);
        tweet.setContent(content);
        tweet.setTitle(title);

        return tweet;
    }
}