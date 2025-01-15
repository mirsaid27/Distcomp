package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Post;


@Repository
public interface IPostRepository extends JpaRepository <Post, Long> {
}
