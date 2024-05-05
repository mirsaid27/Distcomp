package by.bsuir.test_rw.service.dto_convert.interfaces;

import by.bsuir.test_rw.exception.model.dto_convert.ToConvertException;
import by.bsuir.test_rw.model.dto.issue.IssueRequestTO;
import by.bsuir.test_rw.model.dto.issue.IssueResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Creator;
import by.bsuir.test_rw.model.entity.implementations.Issue;
import jakarta.persistence.ManyToMany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueToConverter extends ToConverter<Issue, IssueRequestTO, IssueResponseTO>{
    @Mapping( target = "creator", expression = "java(idToCreator(requestTo.getCreatorId()))")
    Issue convertToEntity(IssueRequestTO requestTo) throws ToConvertException;

    default Creator idToCreator(Long creatorId) {
        Creator creator = new Creator();
        creator.setId(creatorId);
        return creator;
    }

    @Mapping( target = "creatorId", expression = "java(entity.getCreator()!=null?entity.getCreator().getId():null)")
    IssueResponseTO convertToDto(Issue entity) throws ToConvertException;
}
