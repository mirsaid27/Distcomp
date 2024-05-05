package by.bsuir.repository;

import by.bsuir.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    boolean existsByLogin(String login);

    @Query("SELECT i.author FROM Story i WHERE i.id = :storyId")
    Author findAuthorByStoryId(@Param("storyId") Long storyId);

    Page<Author> findAll(Pageable pageable);
}
