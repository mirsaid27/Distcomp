package by.bsuir.discussion.services;


import by.bsuir.discussion.dto.requests.PostRequestDto;
import by.bsuir.discussion.dto.responses.PostResponseDto;

import java.util.List;

public interface PostService extends BaseService<PostRequestDto, PostResponseDto> {
    List<PostResponseDto> readAll();
}
