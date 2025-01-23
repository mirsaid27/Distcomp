package ru.bsuir.irepositories;

import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.CComment;


@Primary
@Repository
public interface CCommentRepository extends CassandraRepository<CComment, Long> {

}