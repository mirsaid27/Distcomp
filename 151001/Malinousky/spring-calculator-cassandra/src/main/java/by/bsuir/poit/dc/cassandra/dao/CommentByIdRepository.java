package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.CommentById;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.Optional;

/**
 * @author Name Surname
 * 
 */
public interface CommentByIdRepository extends MapIdCassandraRepository<CommentById> {
    default Optional<CommentById> findById(long id) {
	return findById(BasicMapId.id("id", id));
    }

    default void deleteById(long id) {
	deleteById(BasicMapId.id("id", id));
    }
}
