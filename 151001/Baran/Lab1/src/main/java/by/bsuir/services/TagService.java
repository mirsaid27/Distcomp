package by.bsuir.services;

import by.bsuir.dao.TagDao;
import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.entities.Tag;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.TagListMapper;
import by.bsuir.mapper.TagMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TagService {
    @Autowired
    TagMapper TagMapper;
    @Autowired
    TagDao TagDao;
    @Autowired
    TagListMapper TagListMapper;

    public TagResponseTo getTagById(@Min(0) Long id) throws NotFoundException {
        Optional<Tag> Tag = TagDao.findById(id);
        return Tag.map(value -> TagMapper.TagToTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found!", 40004L));
    }

    public List<TagResponseTo> getTags() {
        return TagListMapper.toTagResponseList(TagDao.findAll());
    }

    public TagResponseTo saveTag(@Valid TagRequestTo Tag) {
        Tag TagToSave = TagMapper.TagRequestToTag(Tag);
        return TagMapper.TagToTagResponse(TagDao.save(TagToSave));
    }

    public void deleteTag(@Min(0) Long id) throws DeleteException {
        TagDao.delete(id);
    }

    public TagResponseTo updateTag(@Valid TagRequestTo Tag) throws UpdateException {
        Tag TagToUpdate = TagMapper.TagRequestToTag(Tag);
        return TagMapper.TagToTagResponse(TagDao.update(TagToUpdate));
    }

    public TagResponseTo getTagByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Tag> Tag = TagDao.getTagByIssueId(issueId);
        return Tag.map(value -> TagMapper.TagToTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found!", 40004L));
    }
}
