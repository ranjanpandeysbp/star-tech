package com.ims.repository;

import com.ims.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByProductNameContaining(String productName);

    List<ProductEntity> findAllByMerchantId(Long merchantId);
    List<ProductEntity> findByMerchantIdAndCreatedAtBetween(Long merchantId, LocalDateTime startDate, LocalDateTime endDate);
}