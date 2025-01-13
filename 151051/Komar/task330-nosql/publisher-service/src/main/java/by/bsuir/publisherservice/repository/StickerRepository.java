package by.bsuir.publisherservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.bsuir.publisherservice.entity.Sticker;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

}
