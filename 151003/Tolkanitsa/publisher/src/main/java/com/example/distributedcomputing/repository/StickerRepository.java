package com.example.distributedcomputing.repository;

import com.example.distributedcomputing.model.entity.Sticker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends CrudRepository<Sticker, Long> {
}
