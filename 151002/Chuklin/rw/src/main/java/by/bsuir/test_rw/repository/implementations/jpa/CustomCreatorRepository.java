package by.bsuir.test_rw.repository.implementations.jpa;

import by.bsuir.test_rw.model.entity.implementations.Creator;
import by.bsuir.test_rw.repository.interfaces.CreatorRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomCreatorRepository extends JpaRepository<Creator, Long>, CreatorRepository {
}
