package by.harlap.jpa.service.impl;

import by.harlap.jpa.exception.EntityNotFoundException;
import by.harlap.jpa.model.Editor;
import by.harlap.jpa.repository.impl.EditorRepository;
import by.harlap.jpa.util.entity.EditorTestBuilder;
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
class EditorServiceImplTest {

    @InjectMocks
    private EditorServiceImpl editorService;

    @Mock
    private EditorRepository editorRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Editor expected = EditorTestBuilder.editor().build();

            doReturn(expected)
                    .when(editorRepository)
                    .save(expected);

            final Editor actual = editorService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long editorId = 1L;
            final String expectedMessage = EditorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(editorId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(editorRepository)
                    .findById(editorId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> editorService.findById(editorId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Editor expected = EditorTestBuilder.editor().build();

            doReturn(Optional.of(expected))
                    .when(editorRepository)
                    .findById(expected.getId());

            final Editor actual = editorService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Editor editor = EditorTestBuilder.editor().build();
            final List<Editor> editorList = List.of(editor);

            doReturn(editorList)
                    .when(editorRepository)
                    .findAll();

            final List<Editor> actual = editorService.findAll();

            assertThat(actual).contains(editor);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long editorId = 1L;
            final String expectedMessage = EditorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(editorId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(editorRepository)
                    .findById(editorId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> editorService.deleteById(editorId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long editorId = 1L;
            final Editor editor = EditorTestBuilder.editor().build();

            doReturn(Optional.of(editor))
                    .when(editorRepository)
                    .findById(editorId);

            doNothing().when(editorRepository)
                    .deleteById(editorId);

            editorService.deleteById(editorId);

            verify(editorRepository, times(1)).deleteById(editorId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Editor editor = EditorTestBuilder.editor().build();
            final String expectedMessage = EditorServiceImpl.AUTHOR_NOT_FOUND_MESSAGE.formatted(editor.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(editorRepository)
                    .findById(editor.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> editorService.update(editor));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Editor expected = EditorTestBuilder.editor().build();

            doReturn(Optional.of(expected))
                    .when(editorRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(editorRepository)
                    .save(expected);

            final Editor actual = editorService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }
}