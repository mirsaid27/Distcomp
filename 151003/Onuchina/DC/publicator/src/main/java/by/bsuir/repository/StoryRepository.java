package by.bsuir.repository;

import by.bsuir.entities.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long>, JpaSpecificationExecutor<Story> {
    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT i FROM Story i " +
            "LEFT JOIN i.tags t " +
            "WHERE (:tagNames IS NULL OR t.name IN :tagNames) " +
            "AND (:tagIds IS NULL OR t.id IN :tagIds) " +
            "AND (:authorLogin IS NULL OR i.author.login = :authorLogin) " +
            "AND (:title IS NULL OR i.title LIKE %:title%) " +
            "AND (:content IS NULL OR i.content LIKE %:content%)")
    List<Story> findStoriesByParams(@Param("tagNames") List<String> tagNames,
                                   @Param("tagIds") List<Long> tagIds,
                                   @Param("authorLogin") String authorLogin,
                                   @Param("title") String title,
                                   @Param("content") String content);

    Page<Story> findAll(Pageable pageable);
}
