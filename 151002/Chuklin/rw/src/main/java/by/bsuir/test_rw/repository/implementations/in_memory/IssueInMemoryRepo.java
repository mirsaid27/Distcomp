package by.bsuir.test_rw.repository.implementations.in_memory;

import by.bsuir.test_rw.model.entity.implementations.Issue;
import by.bsuir.test_rw.repository.interfaces.IssueRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IssueInMemoryRepo extends InMemoryRepoLongId<Issue> implements IssueRepository {
}
