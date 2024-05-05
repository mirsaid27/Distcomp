package by.bsuir.egor.repositories;

import by.bsuir.egor.Entity.Sticker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StickerRepository extends CrudRepository<Sticker,Long> {
    Sticker findById(long id);

    List<Sticker> findAll();

    Sticker save(Sticker sticker);

    void deleteById(long id);
}
