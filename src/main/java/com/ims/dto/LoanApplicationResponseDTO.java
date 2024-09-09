package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private UserDTO merchant;
    private UserDTO lender;
    private Double loanAmountRequested;
    private String currency;
    private String eLoanStatusLender;
    private String eLoanStatusMerchant;
    private Double riskScore;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
