package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.CreateTweetDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class CreateTweetDtoTestBuilder implements TestBuilder<CreateTweetDto> {

    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public CreateTweetDto build() {
        CreateTweetDto tweet = new CreateTweetDto();

        tweet.setAuthorId(authorId);
        tweet.setContent(content);
        tweet.setTitle(title);

        return tweet;
    }
}

