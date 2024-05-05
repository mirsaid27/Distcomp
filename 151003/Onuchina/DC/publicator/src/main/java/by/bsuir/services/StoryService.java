package by.bsuir.services;

import by.bsuir.dto.StoryRequestTo;
import by.bsuir.dto.StoryResponseTo;
import by.bsuir.entities.Story;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.StoryListMapper;
import by.bsuir.mapper.StoryMapper;
import by.bsuir.repository.AuthorRepository;
import by.bsuir.repository.StoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@CacheConfig(cacheNames = "storyCache")
public class StoryService {
    @Autowired
    StoryMapper storyMapper;
    @Autowired
    StoryRepository storyDao;
    @Autowired
    StoryListMapper storyListMapper;
    @Autowired
    AuthorRepository authorRepository;

    @Cacheable(value = "storys", key = "#id", unless = "#result == null")
    public StoryResponseTo getStoryById(@Min(0) Long id) throws NotFoundException {
        Optional<Story> story = storyDao.findById(id);
        return story.map(value -> storyMapper.storyToStoryResponse(value)).orElseThrow(() -> new NotFoundException("Story not found!", 40004L));
    }

    @Cacheable(cacheNames = "storys")
    public List<StoryResponseTo> getStories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Story> stories = storyDao.findAll(pageable);
        return storyListMapper.toStoryResponseList(stories.toList());
    }

    @CacheEvict(cacheNames = "storys", allEntries = true)
    public StoryResponseTo saveStory(@Valid StoryRequestTo story) throws DuplicationException {
        Story storyToSave = storyMapper.storyRequestToStory(story);
        if (storyDao.existsByTitle(storyToSave.getTitle())) {
            throw new DuplicationException("Title duplication", 40005L);
        }
        if (story.getAuthorId() != null) {
            storyToSave.setAuthor(authorRepository.findById(story.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found!", 40004L)));
        }
        return storyMapper.storyToStoryResponse(storyDao.save(storyToSave));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "storys", key = "#id"),
            @CacheEvict(cacheNames = "storys", allEntries = true) })
    public void deleteStory(@Min(0) Long id) throws DeleteException {
        if (!storyDao.existsById(id)) {
            throw new DeleteException("Story not found!", 40004L);
        } else {
            storyDao.deleteById(id);
        }
    }

    @CacheEvict(cacheNames = "storys", allEntries = true)
    public StoryResponseTo updateStory(@Valid StoryRequestTo story) throws UpdateException {
        Story storyToUpdate = storyMapper.storyRequestToStory(story);
        if (!storyDao.existsById(story.getId())) {
            throw new UpdateException("Story not found!", 40004L);
        } else {
            if (story.getAuthorId() != null) {
                storyToUpdate.setAuthor(authorRepository.findById(story.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found!", 40004L)));
            }
            return storyMapper.storyToStoryResponse(storyDao.save(storyToUpdate));
        }
    }

    public List<StoryResponseTo> getStoryByCriteria(List<String> tagName, List<Long> tagId, String authorLogin, String title, String content) {
        return storyListMapper.toStoryResponseList(storyDao.findStoriesByParams(tagName, tagId, authorLogin, title, content));
    }
}
