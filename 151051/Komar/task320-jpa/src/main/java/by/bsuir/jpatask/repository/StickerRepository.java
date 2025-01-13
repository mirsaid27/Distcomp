package by.bsuir.jpatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsuir.jpatask.entity.Sticker;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

}
