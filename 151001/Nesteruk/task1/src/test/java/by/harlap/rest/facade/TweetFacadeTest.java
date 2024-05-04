package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateTweetDto;
import by.harlap.rest.dto.request.UpdateTweetDto;
import by.harlap.rest.dto.response.TweetResponseDto;
import by.harlap.rest.mapper.TweetMapper;
import by.harlap.rest.model.Tweet;
import by.harlap.rest.service.TweetService;
import by.harlap.rest.util.dto.request.CreateTweetDtoTestBuilder;
import by.harlap.rest.util.dto.request.UpdateTweetDtoTestBuilder;
import by.harlap.rest.util.dto.response.TweetResponseDtoTestBuilder;
import by.harlap.rest.util.entity.DetachedTweetTestBuilder;
import by.harlap.rest.util.entity.TweetTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TweetFacadeTest {

    @InjectMocks
    private TweetFacade tweetFacade;

    @Mock
    private TweetService tweetService;

    @Mock
    private TweetMapper tweetMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Tweet persistedTweet = TweetTestBuilder.tweet().build();
        final TweetResponseDto expected = TweetResponseDtoTestBuilder.tweet().build();

        doReturn(persistedTweet)
                .when(tweetService)
                .findById(persistedTweet.getId());

        doReturn(expected)
                .when(tweetMapper)
                .toTweetResponse(persistedTweet);

        final TweetResponseDto actual = tweetFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Tweet persistedTweet = TweetTestBuilder.tweet().build();
        final TweetResponseDto expectedTweet = TweetResponseDtoTestBuilder.tweet().build();
        final List<TweetResponseDto> expected = List.of(expectedTweet);

        doReturn(List.of(persistedTweet))
                .when(tweetService)
                .findAll();

        doReturn(expectedTweet)
                .when(tweetMapper)
                .toTweetResponse(persistedTweet);

        final List<TweetResponseDto> actual = tweetFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Tweet detachedTweet = DetachedTweetTestBuilder.tweet().build();
        final CreateTweetDto request = CreateTweetDtoTestBuilder.tweet().build();
        final Tweet persistedTweet = TweetTestBuilder.tweet().build();
        final TweetResponseDto expected = TweetResponseDtoTestBuilder.tweet().build();

        doReturn(detachedTweet)
                .when(tweetMapper)
                .toTweet(request);

        doReturn(persistedTweet)
                .when(tweetService)
                .save(detachedTweet);

        doReturn(expected)
                .when(tweetMapper)
                .toTweetResponse(persistedTweet);

        final TweetResponseDto actual = tweetFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateTweetDto request = UpdateTweetDtoTestBuilder.tweet().build();
        final Tweet persistedTweet = TweetTestBuilder.tweet().build();
        final TweetResponseDto expected = TweetResponseDtoTestBuilder.tweet().build();

        doReturn(persistedTweet)
                .when(tweetService)
                .findById(persistedTweet.getId());

        doReturn(persistedTweet)
                .when(tweetMapper)
                .toTweet(request, persistedTweet);

        doReturn(persistedTweet)
                .when(tweetService)
                .update(persistedTweet);

        doReturn(expected)
                .when(tweetMapper)
                .toTweetResponse(persistedTweet);

        final TweetResponseDto actual = tweetFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long tweetId = 1L;

        doNothing().when(tweetService)
                .deleteById(tweetId);

        tweetFacade.delete(tweetId);

        verify(tweetService, times(1)).deleteById(tweetId);
    }
}
