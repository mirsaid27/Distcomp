package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.UpdateTweetDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class UpdateTweetDtoTestBuilder implements TestBuilder<UpdateTweetDto> {

    private Long id = 1L;
    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public UpdateTweetDto build() {
        UpdateTweetDto tweet = new UpdateTweetDto();

        tweet.setId(id);
        tweet.setAuthorId(authorId);
        tweet.setContent(content);
        tweet.setTitle(title);

        return tweet;
    }
}