package com.ims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanOffers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double minLoanAmount;
    private Double maxLoanAmount;
    private Double minInterestRate;
    private Double maxInterestRate;
    private String loanCriteria;
    @ManyToOne
    private UserEntity lender;
}
