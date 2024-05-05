package by.bsuir.egor.repositories;

import by.bsuir.egor.Entity.Editor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorRepository extends CrudRepository<Editor,Long> {

    Editor findById(long id);

    List<Editor> findAll();

    Editor save(Editor editor);

    void deleteById(long id);
}
