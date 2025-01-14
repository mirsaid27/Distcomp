package by.bsuir.dc.impl.story;
import by.bsuir.dc.api.RestService;
import by.bsuir.dc.impl.story.model.Story;
import by.bsuir.dc.impl.story.model.StoryRequest;
import by.bsuir.dc.impl.story.model.StoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@AllArgsConstructor
public class StoryService implements RestService<StoryRequest, StoryResponse> {
    private StoryMapper StoryMapper;
    private StoryRepository storyCrudRepository;

    @Override
    public List<StoryResponse> findAll() {
        Iterable<Story> stories = storyCrudRepository.getAll();
        return StoryMapper.toStoryResponseDto(stories);
    }
    @Override
    public StoryResponse findById(long id) {
        Story story = storyCrudRepository.getBy(id).orElseThrow();
        return StoryMapper.toStoryResponseDto(story);
    }
    @Override
    public StoryResponse create(StoryRequest request) {
        return storyCrudRepository
                .save(StoryMapper.toStoryDto(request))
                .map(StoryMapper::toStoryResponseDto)
                .orElseThrow();
    }
    @Override
    public StoryResponse update(StoryRequest request) {
        return storyCrudRepository
                .update(StoryMapper.toStoryDto(request))
                .map(StoryMapper::toStoryResponseDto)
                .orElseThrow();
    }
    @Override
    public boolean removeById(long id) {
        if (!storyCrudRepository.deleteById(id)) {
            throw new NoSuchElementException("Element not found");
        }
        return true;
    }
}
