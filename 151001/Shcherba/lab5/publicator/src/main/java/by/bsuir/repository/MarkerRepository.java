package by.bsuir.repository;

import by.bsuir.entities.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, Long>, JpaSpecificationExecutor<Marker> {
    @Query("SELECT i.markers FROM Tweet i WHERE i.id = :tweetId")
    List<Marker> findMarkersByTweetId(@Param("tweetId") Long tweetId);

    Page<Marker> findAll(Pageable pageable);
}
