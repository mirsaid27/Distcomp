package by.bsuir.services;

import by.bsuir.dto.TagRequestTo;
import by.bsuir.dto.TagResponseTo;
import by.bsuir.entities.Tag;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.TagListMapper;
import by.bsuir.mapper.TagMapper;
import by.bsuir.repository.TagRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TagRepository tagDao;
    @Autowired
    TagListMapper tagListMapper;

    public TagResponseTo getTagById(@Min(0) Long id) throws NotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(value -> tagMapper.tagToTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found!", 40004L));
    }

    public List<TagResponseTo> getTags(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Tag> tags = tagDao.findAll(pageable);
        return tagListMapper.toTagResponseList(tags.toList());
    }

    public TagResponseTo saveTag(@Valid TagRequestTo tag) {
        Tag tagToSave = tagMapper.tagRequestToTag(tag);
        return tagMapper.tagToTagResponse(tagDao.save(tagToSave));
    }

    public void deleteTag(@Min(0) Long id) throws DeleteException {
        if (!tagDao.existsById(id)) {
            throw new DeleteException("Tag not found!", 40004L);
        } else {
            tagDao.deleteById(id);
        }
    }

    public TagResponseTo updateTag(@Valid TagRequestTo tag) throws UpdateException {
        Tag tagToUpdate = tagMapper.tagRequestToTag(tag);
        if (!tagDao.existsById(tagToUpdate.getId())){
            throw new UpdateException("Tag not found!", 40004L);
        } else {
            return tagMapper.tagToTagResponse(tagDao.save(tagToUpdate));
        }
    }

    public List<TagResponseTo> getTagByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Tag> tag = tagDao.findTagsByIssueId(issueId);
        return tagListMapper.toTagResponseList(tag);
    }
}
