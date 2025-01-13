package by.bsuir.resttask.repository.inmemory;

import by.bsuir.resttask.entity.News;
import by.bsuir.resttask.repository.NewsRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


@Repository
@Profile("in-memory-repositories")
public class NewsInMemoryRepository extends InMemoryRepository<News>
        implements NewsRepository {

}
