package by.molchan.Distcomp.repositories;

import by.molchan.Distcomp.models.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorsRepository extends JpaRepository<Creator, Long> {
    boolean existsByLogin(String login);
}
