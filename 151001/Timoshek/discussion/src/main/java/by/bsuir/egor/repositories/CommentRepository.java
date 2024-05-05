package by.bsuir.egor.repositories;

import by.bsuir.egor.Entity.Comment;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, Long> {
    @Query("select * from comment where id = ?0 allow filtering;")
    Optional<Comment> findById(Long id);
}

