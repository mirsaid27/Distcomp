package by.bsuir.dao.impl;

import by.bsuir.dao.MarkerDao;
import by.bsuir.entities.Marker;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MarkerDaoImpl implements MarkerDao {

    private long counter = 0;
    private final Map<Long, Marker> map = new HashMap<>();

    @Override
    public Marker save(Marker entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The marker has not been deleted", 40003L);
        }
    }

    @Override
    public List<Marker> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Marker> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Marker update(Marker updatedMarker) throws UpdateException {
        Long id = updatedMarker.getId();

        if (map.containsKey(id)) {
            Marker existingMarker = map.get(id);
            BeanUtils.copyProperties(updatedMarker, existingMarker);
            return existingMarker;
        } else {
            throw new UpdateException("Marker update failed", 40002L);
        }
    }
}
