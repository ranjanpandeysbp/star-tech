package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanOfferDTO {
    private Long id;
    private Double minLoanAmount;
    private Double maxLoanAmount;
    private Double minInterestRate;
    private Double maxInterestRate;
    private String loanCriteria;
    private String industry;
    private Long lenderId;
}
