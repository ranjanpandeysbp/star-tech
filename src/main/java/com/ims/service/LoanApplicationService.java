package com.ims.service;

import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;

import java.util.List;

public interface LoanApplicationService {
    String applyLoan(LoanApplicationRequestDTO requestDTO);
    LoanApplicationResponseDTO getLoanDetails(Long loanApplicationId);
    List<LoanApplicationResponseDTO> getAllLoanApplicationForLender(Long lenderId);
}
