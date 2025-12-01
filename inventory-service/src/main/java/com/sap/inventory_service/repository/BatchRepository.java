package com.sap.inventory_service.repository;

import com.sap.inventory_service.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByProductIdOrderByExpiryDateAsc(Long productId);
}
