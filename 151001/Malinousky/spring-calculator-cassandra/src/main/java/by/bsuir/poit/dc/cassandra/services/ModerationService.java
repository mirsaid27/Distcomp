package by.bsuir.poit.dc.cassandra.services;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import jakarta.validation.constraints.NotNull;

/**
 * @author Name Surname
 * 
 */
public interface ModerationService {
    ModerationResult verify(@NotNull String content);
    UpdateCommentDto prepareUpdate(@NotNull UpdateCommentDto dto);
    UpdateCommentDto prepareSave(@NotNull UpdateCommentDto dto);
}
