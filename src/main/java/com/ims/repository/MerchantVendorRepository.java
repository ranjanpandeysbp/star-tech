package com.ims.repository;

import com.ims.entity.MerchantVendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantVendorRepository extends JpaRepository<MerchantVendorEntity, Long> {
    List<MerchantVendorEntity> findAllByVendorId(Long vendorId);
}
