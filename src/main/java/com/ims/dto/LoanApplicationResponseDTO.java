package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ims.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApplicationResponseDTO {

    private Long id;
    private UserEntity merchant;
    private UserEntity lender;
    private Double loanAmountRequested;
    private String currency;
    private String eLoanStatusLender;
    private String eLoanStatusMerchant;
    private int riskScore;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long loanOfferId;
}
