package org.example.discussion.repository;

import org.example.discussion.entity.Message;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface MessageRepo extends CassandraRepository<Message, UUID> {
    default Stream<Message> getAll(){
        return findAll().stream();
    }
    default Optional<Message> get(UUID id){
        return findById(id);
    }
    default Optional<Message> create(Message input){
        return Optional.of(save(input));
    }
    default Optional<Message> update(Message input){
        return Optional.of(save(input));
    }
    default boolean delete(UUID id){
        if(existsById(id)){
            deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
