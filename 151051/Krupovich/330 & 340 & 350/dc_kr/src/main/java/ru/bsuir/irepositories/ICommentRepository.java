package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Comment;


@Repository
public interface ICommentRepository extends JpaRepository <Comment, Long> {
}
