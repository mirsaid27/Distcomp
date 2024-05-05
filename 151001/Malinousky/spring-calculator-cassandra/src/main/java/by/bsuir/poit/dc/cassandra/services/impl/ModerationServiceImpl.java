package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateCommentDto;
import by.bsuir.poit.dc.cassandra.model.CommentBuilder;
import by.bsuir.poit.dc.cassandra.services.ModerationResult;
import by.bsuir.poit.dc.cassandra.services.ModerationService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Service
public class ModerationServiceImpl implements ModerationService {
    private final List<String> bookieWords = List.of("fuck", "bitch");

    @Override
    public ModerationResult verify(@NonNull String content) {
	boolean hasBookies = bookieWords.parallelStream()
				 .anyMatch(content::contains);
	if (hasBookies) {
	    return ModerationResult.withReason("The provided content holds bookies");
	}
	return ModerationResult.ok();
    }

    @Override
    public UpdateCommentDto prepareUpdate(@NonNull UpdateCommentDto dto) {
	final UpdateCommentDto output;
	if (dto.content() != null) {
	    var status = switch (verify(dto.content())) {
		case ModerationResult.Error(String _) -> CommentBuilder.Status.DECLINED;
		case ModerationResult.Ok _ -> CommentBuilder.Status.APPROVED;
	    };
	    output = dto.toBuilder()
			 .status(status.id())
			 .build();
	} else {
	    output = dto;
	}
	return output;
    }

    @Override
    public UpdateCommentDto prepareSave(@NonNull UpdateCommentDto dto) {
	assert dto.content() != null : "The initial content should be non null";
	var status = switch (verify(dto.content())) {
	    case ModerationResult.Error(String _) -> CommentBuilder.Status.DECLINED;
	    case ModerationResult.Ok _ -> CommentBuilder.Status.APPROVED;
	};
	return dto.toBuilder()
		   .status(status.id())
		   .build();
    }
}
