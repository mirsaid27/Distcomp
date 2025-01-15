package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Creator;

@Repository
public interface ICreatorRepository extends JpaRepository<Creator, Long> {

}

