package by.bsuir.test_rw.service.dto_convert.interfaces;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.model.dto.note.NoteRequestTO;
import by.bsuir.test_rw.model.dto.note.NoteResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Issue;
import by.bsuir.test_rw.model.entity.implementations.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteToConverter extends ToConverter<Note, NoteRequestTO, NoteResponseTO> {

    @Mapping(target = "issue", expression = "java(idToIssue(requestTo.getIssueId()))")
    Note convertToEntity(NoteRequestTO requestTo) throws ToConvertException;

    default Issue idToIssue(Long issueId) {
        Issue issue = new Issue();
        issue.setId(issueId);
        return issue;
    }

    @Mapping(target = "issueId", expression = "java(entity.getIssue()!=null?entity.getIssue().getId():null)")
    NoteResponseTO convertToDto(Note entity) throws ToConvertException;
}
