package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Author;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.util.entity.AuthorTestBuilder;
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
class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AbstractRepository<Author, Long> authorRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Author expected = AuthorTestBuilder.author().build();

            doReturn(expected)
                    .when(authorRepository)
                    .save(expected);

            final Author actual = authorService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long authorId = 1L;
            final String expectedMessage = AuthorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(authorId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(authorRepository)
                    .findById(authorId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> authorService.findById(authorId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Author expected = AuthorTestBuilder.author().build();

            doReturn(Optional.of(expected))
                    .when(authorRepository)
                    .findById(expected.getId());

            final Author actual = authorService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Author author = AuthorTestBuilder.author().build();
            final List<Author> authorList = List.of(author);

            doReturn(authorList)
                    .when(authorRepository)
                    .findAll();

            final List<Author> actual = authorService.findAll();

            assertThat(actual).contains(author);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long authorId = 1L;
            final String expectedMessage = AuthorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(authorId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(authorRepository)
                    .findById(authorId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> authorService.deleteById(authorId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long authorId = 1L;
            final Author author = AuthorTestBuilder.author().build();

            doReturn(Optional.of(author))
                    .when(authorRepository)
                    .findById(authorId);

            doNothing().when(authorRepository)
                    .deleteById(authorId);

            authorService.deleteById(authorId);

            verify(authorRepository, times(1)).deleteById(authorId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Author author = AuthorTestBuilder.author().build();
            final String expectedMessage = AuthorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(author.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(authorRepository)
                    .findById(author.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> authorService.update(author));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Author expected = AuthorTestBuilder.author().build();

            doReturn(Optional.of(expected))
                    .when(authorRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(authorRepository)
                    .update(expected);

            final Author actual = authorService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }
}