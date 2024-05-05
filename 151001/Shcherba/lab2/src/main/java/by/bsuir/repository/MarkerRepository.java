package by.bsuir.repository;

import by.bsuir.entities.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarkerRepository extends JpaRepository<Marker, Long>, JpaSpecificationExecutor<Marker> {
    Page<Marker> findAll(Pageable pageable);
}
