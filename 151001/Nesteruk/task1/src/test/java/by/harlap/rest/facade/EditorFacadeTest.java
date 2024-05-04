package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateEditorDto;
import by.harlap.rest.dto.request.UpdateEditorDto;
import by.harlap.rest.dto.response.EditorResponseDto;
import by.harlap.rest.mapper.EditorMapper;
import by.harlap.rest.model.Editor;
import by.harlap.rest.service.EditorService;
import by.harlap.rest.util.dto.request.CreateEditorDtoTestBuilder;
import by.harlap.rest.util.dto.request.UpdateEditorDtoTestBuilder;
import by.harlap.rest.util.dto.response.EditorResponseDtoTestBuilder;
import by.harlap.rest.util.entity.EditorTestBuilder;
import by.harlap.rest.util.entity.DetachedEditorTestBuilder;
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
class EditorFacadeTest {

    @InjectMocks
    private EditorFacade editorFacade;

    @Mock
    private EditorService editorService;

    @Mock
    private EditorMapper editorMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Editor persistedEditor = EditorTestBuilder.editor().build();
        final EditorResponseDto expected = EditorResponseDtoTestBuilder.editor().build();

        doReturn(persistedEditor)
                .when(editorService)
                .findById(persistedEditor.getId());

        doReturn(expected)
                .when(editorMapper)
                .toEditorResponse(persistedEditor);

        final EditorResponseDto actual = editorFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Editor persistedEditor = EditorTestBuilder.editor().build();
        final EditorResponseDto expectedEditor = EditorResponseDtoTestBuilder.editor().build();
        final List<EditorResponseDto> expected = List.of(expectedEditor);

        doReturn(List.of(persistedEditor))
                .when(editorService)
                .findAll();

        doReturn(expectedEditor)
                .when(editorMapper)
                .toEditorResponse(persistedEditor);

        final List<EditorResponseDto> actual = editorFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Editor detachedEditor = DetachedEditorTestBuilder.editor().build();
        final CreateEditorDto request = CreateEditorDtoTestBuilder.editor().build();
        final Editor persistedEditor = EditorTestBuilder.editor().build();
        final EditorResponseDto expected = EditorResponseDtoTestBuilder.editor().build();

        doReturn(detachedEditor)
                .when(editorMapper)
                .toEditor(request);

        doReturn(persistedEditor)
                .when(editorService)
                .save(detachedEditor);

        doReturn(expected)
                .when(editorMapper)
                .toEditorResponse(persistedEditor);

        final EditorResponseDto actual = editorFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateEditorDto request = UpdateEditorDtoTestBuilder.editor().build();
        final Editor persistedEditor = EditorTestBuilder.editor().build();
        final EditorResponseDto expected = EditorResponseDtoTestBuilder.editor().build();

        doReturn(persistedEditor)
                .when(editorService)
                .findById(persistedEditor.getId());

        doReturn(persistedEditor)
                .when(editorMapper)
                .toEditor(request, persistedEditor);

        doReturn(persistedEditor)
                .when(editorService)
                .update(persistedEditor);

        doReturn(expected)
                .when(editorMapper)
                .toEditorResponse(persistedEditor);

        final EditorResponseDto actual = editorFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long editorId = 1L;

        doNothing().when(editorService)
                .deleteById(editorId);

        editorFacade.delete(editorId);

        verify(editorService, times(1)).deleteById(editorId);
    }
}