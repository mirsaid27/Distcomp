package by.bsuir.test_rw.repository.implementations.jpa;

import by.bsuir.test_rw.model.entity.implementations.Tag;
import by.bsuir.test_rw.repository.interfaces.TagRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomTagRepository extends JpaRepository<Tag, Long>, TagRepository {
}
