package com.example.delivery_tracking.repository;

import com.example.delivery_tracking.entity.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
    Page<Courier> findAll(Pageable pageable);
}
