package by.harlap.rest.repository.impl;

import by.harlap.rest.model.Tweet;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTweetRepository extends AbstractInMemoryRepository<Tweet> {
}
