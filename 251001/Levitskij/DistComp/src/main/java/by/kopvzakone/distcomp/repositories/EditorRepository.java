package by.kopvzakone.distcomp.repositories;

import by.kopvzakone.distcomp.entities.Editor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public class EditorRepository implements Repo<Editor> {
    private final Map<Long, Editor> memDatabase = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong();

    public EditorRepository() {Editor editor = new Editor();
        editor.setId(counter.incrementAndGet());
        editor.setFirstname("Артур");
        editor.setLastname("Левицкий");
        editor.setPassword("123456");
        editor.setLogin("levitskijartur@gmail.com");
        memDatabase.put(editor.getId(), new Editor());
    }
    @Override
    public Stream<Editor> getAll() {
        return memDatabase.values().stream();
    }

    @Override
    public Optional<Editor> get(long id) {
        return Optional.ofNullable(memDatabase.get(id));
    }

    @Override
    public Optional<Editor> create(Editor input) {
        long id = counter.incrementAndGet();
        input.setId(id);
        memDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Editor> update(Editor input) {
         memDatabase.put(input.getId(), input);
         return Optional.of(input);
    }

    @Override
    public boolean delete(long id) {
        return memDatabase.remove(id) != null;
    }
}
