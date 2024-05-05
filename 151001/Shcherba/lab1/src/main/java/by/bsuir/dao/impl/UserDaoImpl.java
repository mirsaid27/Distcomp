package by.bsuir.dao.impl;

import by.bsuir.dao.UserDao;
import by.bsuir.entities.User;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    private long counter = 0;
    private final Map<Long, User> map = new HashMap<>();

    @Override
    public User save(User entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The User has not been deleted", 40003L);
        }
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public User update(User updatedUser) throws UpdateException {
        Long id = updatedUser.getId();

        if (map.containsKey(id)) {
            User existingUser = map.get(id);
            BeanUtils.copyProperties(updatedUser, existingUser);
            return existingUser;
        } else {
            throw new UpdateException("User update failed", 40002L);
        }
    }
}
