package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Editor;
import ru.bsuir.entity.Label;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {

}

