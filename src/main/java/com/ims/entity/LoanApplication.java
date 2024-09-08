package com.ims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "loan_application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private UserEntity merchant;
    @OneToOne
    private UserEntity lender;
    private Double riskScore;
    private Double loanAmountRequested;
    private EStatus eLoanStatus;
    private List<String> comments;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private ECurrency currency;
}
