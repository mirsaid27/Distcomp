package by.bsuir.repository;

import by.bsuir.entities.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long>, JpaSpecificationExecutor<Label> {
    @Query("SELECT i.labels FROM Issue i WHERE i.id = :issueId")
    List<Label> findLabelsByIssueId(@Param("issueId") Long issueId);

    Page<Label> findAll(Pageable pageable);
}
