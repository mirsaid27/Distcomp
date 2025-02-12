package by.kapinskiy.Task310.repositories.implementations;

import by.kapinskiy.Task310.models.Issue;
import by.kapinskiy.Task310.repositories.IssuesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Repository
public class InMemoryIssuesRepository implements IssuesRepository {
    private final ConcurrentHashMap<Long, Issue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();


    @Override
    public void delete(Issue issue) {
        issues.remove(issue.getId());
    }

    @Override
    public Issue save(Issue issue) {
        long id = issue.getId();
        if (!issues.containsKey(id)) {
            id = idGenerator.incrementAndGet();
            issue.setId(id);
        }

        issues.put(id, issue);
        return issue;
    }

    @Override
    public void deleteById(long id) {
        issues.remove(id);
    }

    @Override
    public Optional<Issue> findById(long id) {
        if (issues.containsKey(id)) {
            return Optional.of(issues.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Issue> findAll() {
        return issues.values().stream().toList();
    }

    @Override
    public boolean existsById(long id) {
        return issues.containsKey(id);
    }
}
