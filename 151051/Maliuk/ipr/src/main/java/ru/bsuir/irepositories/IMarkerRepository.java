package ru.bsuir.irepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuir.entity.Marker;

@Repository
public interface IMarkerRepository extends JpaRepository<Marker, Long> {

}

