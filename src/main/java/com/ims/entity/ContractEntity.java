package com.ims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double approvedLoanAmount;
    private String repaymentCriteria;
    private LocalDate loanRepaymentDate;
    private Double interestRate;
    @OneToOne
    private LoanApplication loanApplication;
    @Lob
    private String document;

}
