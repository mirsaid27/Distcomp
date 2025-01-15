package by.bsuir.dc.impl.story;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.story.model.Story;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface StoryRepository extends AbstractMemoryRepository<Story> {
}
