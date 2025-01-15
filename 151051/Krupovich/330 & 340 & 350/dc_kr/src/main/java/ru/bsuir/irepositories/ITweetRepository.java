package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Tweet;

@Repository
public interface ITweetRepository extends JpaRepository<Tweet, Long> {

}
