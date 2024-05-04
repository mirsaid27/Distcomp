package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateTagDto;
import by.harlap.jpa.dto.request.UpdateTagDto;
import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.mapper.TagMapper;
import by.harlap.jpa.model.Tag;
import by.harlap.jpa.service.TagService;
import by.harlap.jpa.util.dto.tag.request.CreateTagDtoTestBuilder;
import by.harlap.jpa.util.dto.tag.request.UpdateTagDtoTestBuilder;
import by.harlap.jpa.util.dto.tag.response.TagResponseDtoTestBuilder;
import by.harlap.jpa.util.entity.DetachedTagTestBuilder;
import by.harlap.jpa.util.entity.TagTestBuilder;
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
class TagFacadeTest {

    @InjectMocks
    private TagFacade tagFacade;

    @Mock
    private TagService tagService;

    @Mock
    private TagMapper tagMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Tag persistedTag = TagTestBuilder.tag().build();
        final TagResponseDto expected = TagResponseDtoTestBuilder.tag().build();

        doReturn(persistedTag)
                .when(tagService)
                .findById(persistedTag.getId());

        doReturn(expected)
                .when(tagMapper)
                .toTagResponse(persistedTag);

        final TagResponseDto actual = tagFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Tag persistedTag = TagTestBuilder.tag().build();
        final TagResponseDto expectedTag = TagResponseDtoTestBuilder.tag().build();
        final List<TagResponseDto> expected = List.of(expectedTag);

        doReturn(List.of(persistedTag))
                .when(tagService)
                .findAll();

        doReturn(expectedTag)
                .when(tagMapper)
                .toTagResponse(persistedTag);

        final List<TagResponseDto> actual = tagFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Tag detachedTag = DetachedTagTestBuilder.tag().build();
        final CreateTagDto request = CreateTagDtoTestBuilder.tag().build();
        final Tag persistedTag = TagTestBuilder.tag().build();
        final TagResponseDto expected = TagResponseDtoTestBuilder.tag().build();

        doReturn(detachedTag)
                .when(tagMapper)
                .toTag(request);

        doReturn(persistedTag)
                .when(tagService)
                .save(detachedTag);

        doReturn(expected)
                .when(tagMapper)
                .toTagResponse(persistedTag);

        final TagResponseDto actual = tagFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateTagDto request = UpdateTagDtoTestBuilder.tag().build();
        final Tag persistedTag = TagTestBuilder.tag().build();
        final TagResponseDto expected = TagResponseDtoTestBuilder.tag().build();

        doReturn(persistedTag)
                .when(tagService)
                .findById(persistedTag.getId());

        doReturn(persistedTag)
                .when(tagMapper)
                .toTag(request, persistedTag);

        doReturn(persistedTag)
                .when(tagService)
                .update(persistedTag);

        doReturn(expected)
                .when(tagMapper)
                .toTagResponse(persistedTag);

        final TagResponseDto actual = tagFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long tagId = 1L;

        doNothing().when(tagService)
                .deleteById(tagId);

        tagFacade.delete(tagId);

        verify(tagService, times(1)).deleteById(tagId);
    }
}
