package by.bsuir.dao.impl;

import by.bsuir.dao.TweetDao;
import by.bsuir.entities.Tweet;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TweetDaoImpl implements TweetDao {

    private long counter = 0;
    private final Map<Long, Tweet> map = new HashMap<>();

    @Override
    public Tweet save(Tweet entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The tweet has not been deleted", 40003L);
        }
    }

    @Override
    public List<Tweet> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Tweet> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Tweet update(Tweet updatedTweet) throws UpdateException {
        Long id = updatedTweet.getId();

        if (map.containsKey(id)) {
            Tweet existingTweet = map.get(id);
            BeanUtils.copyProperties(updatedTweet, existingTweet);
            return existingTweet;
        } else {
            throw new UpdateException("Tweet update failed", 40002L);
        }
    }
}
