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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@CacheConfig(cacheNames = "tagCache")
public class TagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TagRepository tagDao;
    @Autowired
    TagListMapper tagListMapper;

    @Cacheable(cacheNames = "tags", key = "#id", unless = "#result == null")
    public TagResponseTo getTagById(@Min(0) Long id) throws NotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(value -> tagMapper.tagToTagResponse(value)).orElseThrow(() -> new NotFoundException("Tag not found!", 40004L));
    }

    @Cacheable(cacheNames = "tags")
    public List<TagResponseTo> getTags(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Tag> tags = tagDao.findAll(pageable);
        return tagListMapper.toTagResponseList(tags.toList());
    }

    @CacheEvict(cacheNames = "tags", allEntries = true)
    public TagResponseTo saveTag(@Valid TagRequestTo tag) {
        Tag tagToSave = tagMapper.tagRequestToTag(tag);
        return tagMapper.tagToTagResponse(tagDao.save(tagToSave));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "tags", key = "#id"),
            @CacheEvict(cacheNames = "tags", allEntries = true) })
    public void deleteTag(@Min(0) Long id) throws DeleteException {
        if (!tagDao.existsById(id)) {
            throw new DeleteException("Tag not found!", 40004L);
        } else {
            tagDao.deleteById(id);
        }
    }

    @CacheEvict(cacheNames = "tags", allEntries = true)
    public TagResponseTo updateTag(@Valid TagRequestTo tag) throws UpdateException {
        Tag tagToUpdate = tagMapper.tagRequestToTag(tag);
        if (!tagDao.existsById(tagToUpdate.getId())) {
            throw new UpdateException("Tag not found!", 40004L);
        } else {
            return tagMapper.tagToTagResponse(tagDao.save(tagToUpdate));
        }
    }

    public List<TagResponseTo> getTagByStoryId(@Min(0) Long storyId) throws NotFoundException {
        List<Tag> tag = tagDao.findTagsByStoryId(storyId);
        return tagListMapper.toTagResponseList(tag);
    }
}
