package by.molchan.Distcomp.repositories;

import by.molchan.Distcomp.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticlesRepository extends JpaRepository<Article, Long> {
    boolean existsByTitle(String title);
}
