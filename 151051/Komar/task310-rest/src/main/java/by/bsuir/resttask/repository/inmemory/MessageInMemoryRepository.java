package by.bsuir.resttask.repository.inmemory;

import by.bsuir.resttask.entity.Message;
import by.bsuir.resttask.repository.MessageRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("in-memory-repositories")
public class MessageInMemoryRepository extends InMemoryRepository<Message>
        implements MessageRepository {

}
