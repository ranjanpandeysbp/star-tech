package com.ims.repository;

import com.ims.entity.CategoryEntity;
import com.ims.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    List<ContractEntity> findAllByLoanApplicationMerchantId(Long merchantId);
}
