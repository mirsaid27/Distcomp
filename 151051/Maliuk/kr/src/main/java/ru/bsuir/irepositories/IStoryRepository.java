package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Story;

@Repository
public interface IStoryRepository extends JpaRepository<Story, Long> {
    boolean findByTitle(String title);
}
