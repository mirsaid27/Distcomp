package by.harlap.rest.service.impl;

import by.harlap.rest.exception.EntityNotFoundException;
import by.harlap.rest.model.Tag;
import by.harlap.rest.repository.AbstractRepository;
import by.harlap.rest.util.entity.TagTestBuilder;
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
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private AbstractRepository<Tag, Long> tagRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tag expected = TagTestBuilder.tag().build();

            doReturn(expected)
                    .when(tagRepository)
                    .save(expected);

            final Tag actual = tagService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long tagId = 1L;
            final String expectedMessage = TagServiceImpl.TAG_NOT_FOUND_MESSAGE.formatted(tagId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tagRepository)
                    .findById(tagId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tagService.findById(tagId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tag expected = TagTestBuilder.tag().build();

            doReturn(Optional.of(expected))
                    .when(tagRepository)
                    .findById(expected.getId());

            final Tag actual = tagService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Tag tag = TagTestBuilder.tag().build();
            final List<Tag> tagList = List.of(tag);

            doReturn(tagList)
                    .when(tagRepository)
                    .findAll();

            final List<Tag> actual = tagService.findAll();

            assertThat(actual).contains(tag);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long tagId = 1L;
            final String expectedMessage = TagServiceImpl.TAG_NOT_FOUND_MESSAGE.formatted(tagId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tagRepository)
                    .findById(tagId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tagService.deleteById(tagId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long tagId = 1L;
            final Tag tag = TagTestBuilder.tag().build();

            doReturn(Optional.of(tag))
                    .when(tagRepository)
                    .findById(tagId);

            doNothing().when(tagRepository)
                    .deleteById(tagId);

            tagService.deleteById(tagId);

            verify(tagRepository, times(1)).deleteById(tagId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Tag tag = TagTestBuilder.tag().build();
            final String expectedMessage = TagServiceImpl.TAG_NOT_FOUND_MESSAGE.formatted(tag.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(tagRepository)
                    .findById(tag.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> tagService.update(tag));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Tag expected = TagTestBuilder.tag().build();

            doReturn(Optional.of(expected))
                    .when(tagRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(tagRepository)
                    .update(expected);

            final Tag actual = tagService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

}
