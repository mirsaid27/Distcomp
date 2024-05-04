package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateNoteDto;
import by.harlap.jpa.dto.request.UpdateNoteDto;
import by.harlap.jpa.dto.response.NoteResponseDto;
import by.harlap.jpa.mapper.NoteMapper;
import by.harlap.jpa.model.Note;
import by.harlap.jpa.model.Tweet;
import by.harlap.jpa.service.NoteService;
import by.harlap.jpa.service.TweetService;
import by.harlap.jpa.util.dto.note.request.CreateNoteDtoTestBuilder;
import by.harlap.jpa.util.dto.note.request.UpdateNoteDtoTestBuilder;
import by.harlap.jpa.util.dto.note.response.NoteResponseDtoTestBuilder;
import by.harlap.jpa.util.entity.DetachedNoteTestBuilder;
import by.harlap.jpa.util.entity.NoteTestBuilder;
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
class NoteFacadeTest {

    @InjectMocks
    private NoteFacade noteFacade;

    @Mock
    private NoteService noteService;

    @Mock
    private TweetService tweetService;

    @Mock
    private NoteMapper noteMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Note persistedNote = NoteTestBuilder.note().build();
        final NoteResponseDto expected = NoteResponseDtoTestBuilder.note().build();

        doReturn(persistedNote)
                .when(noteService)
                .findById(persistedNote.getId());

        doReturn(expected)
                .when(noteMapper)
                .toNoteResponse(persistedNote);

        final NoteResponseDto actual = noteFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Note persistedNote = NoteTestBuilder.note().build();
        final NoteResponseDto expectedNote = NoteResponseDtoTestBuilder.note().build();
        final List<NoteResponseDto> expected = List.of(expectedNote);

        doReturn(List.of(persistedNote))
                .when(noteService)
                .findAll();

        doReturn(expectedNote)
                .when(noteMapper)
                .toNoteResponse(persistedNote);

        final List<NoteResponseDto> actual = noteFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Note detachedNote = DetachedNoteTestBuilder.note().build();
        final Tweet tweet = TweetTestBuilder.tweet().build();
        final CreateNoteDto request = CreateNoteDtoTestBuilder.note().build();
        final Note persistedNote = NoteTestBuilder.note().build();
        final NoteResponseDto expected = NoteResponseDtoTestBuilder.note().build();

        doReturn(detachedNote)
                .when(noteMapper)
                .toNote(request);

        doReturn(persistedNote)
                .when(noteService)
                .save(detachedNote);

        doReturn(expected)
                .when(noteMapper)
                .toNoteResponse(persistedNote);

        doReturn(tweet)
                .when(tweetService)
                .findById(request.getTweetId());

        final NoteResponseDto actual = noteFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateNoteDto request = UpdateNoteDtoTestBuilder.note().build();
        final Note persistedNote = NoteTestBuilder.note().build();
        final Tweet tweet = TweetTestBuilder.tweet().build();
        final NoteResponseDto expected = NoteResponseDtoTestBuilder.note().build();

        doReturn(persistedNote)
                .when(noteService)
                .findById(persistedNote.getId());

        doReturn(persistedNote)
                .when(noteMapper)
                .toNote(request, persistedNote);

        doReturn(persistedNote)
                .when(noteService)
                .update(persistedNote);

        doReturn(expected)
                .when(noteMapper)
                .toNoteResponse(persistedNote);

        doReturn(tweet)
                .when(tweetService)
                .findById(request.getTweetId());

        final NoteResponseDto actual = noteFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long noteId = 1L;

        doNothing().when(noteService)
                .deleteById(noteId);

        noteFacade.delete(noteId);

        verify(noteService, times(1)).deleteById(noteId);
    }

}
