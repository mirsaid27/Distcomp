package by.bsuir.discussion.services;


import by.bsuir.discussion.dto.requests.NoteRequestDto;
import by.bsuir.discussion.dto.responses.NoteResponseDto;

import java.util.List;

public interface NoteService extends BaseService<NoteRequestDto, NoteResponseDto> {
    List<NoteResponseDto> readAll();
}
