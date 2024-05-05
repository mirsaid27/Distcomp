package by.bsuir.test_rw.repository.implementations.in_memory;

import by.bsuir.test_rw.model.entity.implementations.Tag;
import by.bsuir.test_rw.repository.interfaces.TagRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TagInMemoryRepo extends InMemoryRepoLongId<Tag> implements TagRepository {
}
