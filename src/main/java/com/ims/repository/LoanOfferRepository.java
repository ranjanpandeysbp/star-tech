package com.ims.repository;

import com.ims.entity.LoanApplication;
import com.ims.entity.LoanOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanOfferRepository extends JpaRepository<LoanOffers, Long> {
    List<LoanOffers> findAllBetweenMinLoanAmountAndMaxLoanAmount(Double requestedLoanAmt);
}
