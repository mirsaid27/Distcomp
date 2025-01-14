package by.bsuir.dc.impl.story;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.story.model.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoryRepository extends AbstractMemoryRepository<Story> {
    @Override
    public Optional<Story> update(Story story) {
        long id = story.getId();
        if(Objects.isNull(map.get(id))) {
            throw new NoSuchElementException("update failed");
        }
        Story memoryStory = map.get(id);
        Optional.of(story.getAuthorId()).ifPresent(memoryStory::setAuthorId);
        Optional.of(story.getTitle()).ifPresent(memoryStory::setTitle);
        Optional.of(story.getContent()).ifPresent(memoryStory::setContent);

        map.put(id, memoryStory);
        return Optional.of(memoryStory);
    }
    @Override
    public boolean deleteById(long id) {
        return map.remove(id) != null;
    }
}
