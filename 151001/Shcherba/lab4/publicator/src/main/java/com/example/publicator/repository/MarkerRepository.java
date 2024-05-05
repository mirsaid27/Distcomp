package com.example.publicator.repository;

import com.example.publicator.model.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarkerRepository extends JpaRepository<Marker,Integer> {
    Page<Marker> findAll(Pageable pageable);
}
