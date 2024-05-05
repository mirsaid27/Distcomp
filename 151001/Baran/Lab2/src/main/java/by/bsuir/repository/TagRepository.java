package by.bsuir.repository;

import by.bsuir.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    @Query("SELECT i.tags FROM Issue i WHERE i.id = :issueId")
    List<Tag> findTagsByIssueId(@Param("issueId") Long issueId);
    Page<Tag> findAll(Pageable pageable);
}
