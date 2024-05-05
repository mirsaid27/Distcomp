package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.repository.impl.TweetRepository;
import by.harlap.jpa.util.entity.TweetTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tweet expected = TweetTestBuilder.tweet().build();

            doReturn(expected)
                    .when(tweetRepository)
                    .save(expected);

            final Tweet actual = tweetService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long tweetId = 1L;
            final String expectedMessage = TweetServiceImpl.TWEET_NOT_FOUND_MESSAGE.formatted(tweetId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tweetRepository)
                    .findById(tweetId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tweetService.findById(tweetId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tweet expected = TweetTestBuilder.tweet().build();

            doReturn(Optional.of(expected))
                    .when(tweetRepository)
                    .findById(expected.getId());

            final Tweet actual = tweetService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Tweet tweet = TweetTestBuilder.tweet().build();
            final List<Tweet> tweetList = List.of(tweet);

            doReturn(tweetList)
                    .when(tweetRepository)
                    .findAll();

            final List<Tweet> actual = tweetService.findAll();

            assertThat(actual).contains(tweet);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long tweetId = 1L;
            final String expectedMessage = TweetServiceImpl.TWEET_NOT_FOUND_MESSAGE.formatted(tweetId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tweetRepository)
                    .findById(tweetId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tweetService.deleteById(tweetId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long tweetId = 1L;
            final Tweet tweet = TweetTestBuilder.tweet().build();

            doReturn(Optional.of(tweet))
                    .when(tweetRepository)
                    .findById(tweetId);

            doNothing().when(tweetRepository)
                    .deleteById(tweetId);

            tweetService.deleteById(tweetId);

            verify(tweetRepository, times(1)).deleteById(tweetId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Tweet tweet = TweetTestBuilder.tweet().build();
            final String expectedMessage = TweetServiceImpl.TWEET_NOT_FOUND_MESSAGE.formatted(tweet.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tweetRepository)
                    .findById(tweet.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tweetService.update(tweet));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tweet expected = TweetTestBuilder.tweet().build();

            doReturn(Optional.of(expected))
                    .when(tweetRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(tweetRepository)
                    .save(expected);

            final Tweet actual = tweetService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

}
