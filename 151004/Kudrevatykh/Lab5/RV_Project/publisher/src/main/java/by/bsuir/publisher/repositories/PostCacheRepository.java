package by.bsuir.publisher.repositories;

import by.bsuir.publisher.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCacheRepository extends CrudRepository<Post, Long> {
}
