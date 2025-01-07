package com.example.repository;

import com.example.entities.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface StickerRepository extends JpaRepository<Sticker, Long>, JpaSpecificationExecutor<Sticker> {

    @Query("SELECT i.stickers FROM Issue i WHERE i.id = :issueId")
    List<Sticker> findStickersByIssueId(@Param("issueId") Long issueId);

    Page<Sticker> findAll(Pageable pageable);

    // Метод для проверки существования стикера по id (BigInteger)
    boolean existsById(BigInteger id);

    // Метод для удаления стикера по id (BigInteger)
    void deleteById(BigInteger id);

    // Метод для получения стикера по id (BigInteger)
    Optional<Sticker> findById(BigInteger id);
}


