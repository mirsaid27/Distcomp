package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.dto.request.UpdateTweetDto;
import by.harlap.jpa.dto.response.TweetResponseDto;
import by.harlap.jpa.mapper.TweetMapper;
import by.harlap.jpa.model.Editor;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.service.EditorService;
import by.harlap.jpa.service.TweetService;
import by.harlap.jpa.util.dto.tweet.request.CreateTweetDtoTestBuilder;
import by.harlap.jpa.util.dto.tweet.request.UpdateTweetDtoTestBuilder;
import by.harlap.jpa.util.dto.tweet.response.TweetResponseDtoTestBuilder;
import by.harlap.jpa.util.entity.EditorTestBuilder;
import by.harlap.jpa.util.entity.DetachedTweetTestBuilder;
import by.harlap.jpa.util.entity.TweetTestBuilder;
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

    @Mock
    private EditorService editorService;

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
        final Editor editor = EditorTestBuilder.editor().build();
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

        doReturn(editor)
                .when(editorService)
                .findById(request.getEditorId());

        final TweetResponseDto actual = tweetFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateTweetDto request = UpdateTweetDtoTestBuilder.tweet().build();
        final Tweet persistedTweet = TweetTestBuilder.tweet().build();
        final Editor editor = EditorTestBuilder.editor().build();
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

        doReturn(editor)
                .when(editorService)
                .findById(request.getEditorId());

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
