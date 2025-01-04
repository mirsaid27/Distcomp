package com.example.dao.impl;

import com.example.dao.StickerDao;
import com.example.entities.Sticker;
import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StickerDaoImpl implements StickerDao {
    private long counter = 0;
    private final Map<Long, Sticker> map = new HashMap<>();

    @Override
    public Sticker save(Sticker entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The sticker has not been deleted", 40003L);
        }
    }

    @Override
    public List<Sticker> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Sticker> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Sticker update(Sticker updatedSticker) throws UpdateException {
        Long id = updatedSticker.getId();

        if (map.containsKey(id)) {
            Sticker existingSticker = map.get(id);
            BeanUtils.copyProperties(updatedSticker, existingSticker);
            return existingSticker;
        } else {
            throw new UpdateException("Sticker update failed", 40002L);
        }
    }
}
