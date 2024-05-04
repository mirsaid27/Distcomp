package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Note;
import by.harlap.rest.model.Tweet;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.service.TweetService;
import by.harlap.rest.util.entity.NoteTestBuilder;
import by.harlap.rest.util.entity.TweetTestBuilder;
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
class NoteServiceImplTest {

    @InjectMocks
    private NoteServiceImpl noteService;

    @Mock
    private TweetService tweetService;

    @Mock
    private AbstractRepository<Note, Long> noteRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Note expected = NoteTestBuilder.note().build();
            final Tweet tweet = TweetTestBuilder.tweet().build();

            doReturn(tweet)
                    .when(tweetService)
                    .findById(expected.getTweetId());

            doReturn(expected)
                    .when(noteRepository)
                    .save(expected);

            final Note actual = noteService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long noteId = 1L;
            final String expectedMessage = NoteServiceImpl.NOTE_NOT_FOUND_MESSAGE.formatted(noteId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(noteRepository)
                    .findById(noteId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> noteService.findById(noteId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Note expected = NoteTestBuilder.note().build();

            doReturn(Optional.of(expected))
                    .when(noteRepository)
                    .findById(expected.getId());

            final Note actual = noteService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Note note = NoteTestBuilder.note().build();
            final List<Note> noteList = List.of(note);

            doReturn(noteList)
                    .when(noteRepository)
                    .findAll();

            final List<Note> actual = noteService.findAll();

            assertThat(actual).contains(note);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long noteId = 1L;
            final String expectedMessage = NoteServiceImpl.NOTE_NOT_FOUND_MESSAGE.formatted(noteId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(noteRepository)
                    .findById(noteId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> noteService.deleteById(noteId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long noteId = 1L;
            final Note note = NoteTestBuilder.note().build();

            doReturn(Optional.of(note))
                    .when(noteRepository)
                    .findById(noteId);

            doNothing().when(noteRepository)
                    .deleteById(noteId);

            noteService.deleteById(noteId);

            verify(noteRepository, times(1)).deleteById(noteId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Note note = NoteTestBuilder.note().build();
            final String expectedMessage = NoteServiceImpl.NOTE_NOT_FOUND_MESSAGE.formatted(note.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(noteRepository)
                    .findById(note.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> noteService.update(note));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Note expected = NoteTestBuilder.note().build();
            final Tweet tweet = TweetTestBuilder.tweet().build();

            doReturn(tweet)
                    .when(tweetService)
                    .findById(expected.getTweetId());

            doReturn(Optional.of(expected))
                    .when(noteRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(noteRepository)
                    .update(expected);

            final Note actual = noteService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }
}