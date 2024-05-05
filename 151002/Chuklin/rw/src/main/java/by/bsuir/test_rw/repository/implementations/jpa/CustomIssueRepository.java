package by.bsuir.test_rw.repository.implementations.jpa;

import by.bsuir.test_rw.model.entity.implementations.Issue;
import by.bsuir.test_rw.repository.interfaces.IssueRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomIssueRepository extends JpaRepository<Issue, Long>, IssueRepository {
}
