package com.ims.repository;

import com.ims.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
    List<SalesEntity> findAllByMerchantId(Long merchantId);

    List<SalesEntity>  findByMerchantIdAndCreatedAtBetween(Long merchantId,LocalDateTime startDate, LocalDateTime endDate);
}
