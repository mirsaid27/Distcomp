package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Editor;

@Repository
public interface IEditorRepository extends JpaRepository<Editor, Long> {
    boolean existsByLogin(String login);
}

