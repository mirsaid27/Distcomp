package by.bsuir.test_rw.repository.implementations.in_memory;

import by.bsuir.test_rw.model.entity.implementations.Creator;
import by.bsuir.test_rw.repository.interfaces.CreatorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CreatorInMemoryRepo extends InMemoryRepoLongId<Creator> implements CreatorRepository {
}
