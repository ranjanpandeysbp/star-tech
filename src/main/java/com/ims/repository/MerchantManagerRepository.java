package com.ims.repository;

import com.ims.entity.MerchantManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantManagerRepository extends JpaRepository<MerchantManagerEntity, Long> {
    List<MerchantManagerEntity> findAllByMerchantId(Long merchantId);
}
