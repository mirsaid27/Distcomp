package by.bsuir.resttask.repository.inmemory;

import by.bsuir.resttask.entity.Sticker;
import by.bsuir.resttask.repository.StickerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


@Repository
@Profile("in-memory-repositories")
public class StickerInMemoryRepository extends InMemoryRepository<Sticker>
        implements StickerRepository {

}
