package com.ims.repository;

import com.ims.entity.LoanOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanOfferRepository extends JpaRepository<LoanOffers, Long> {
    List<LoanOffers> findByMinLoanAmountLessThanAndMaxLoanAmountGreaterThan(Double requestedLoanAmt1, Double requestedLoanAmt2);
    List<LoanOffers> findAllByLenderId(Long lenderId);
}
