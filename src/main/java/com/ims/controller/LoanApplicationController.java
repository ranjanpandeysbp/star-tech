package com.ims.controller;

import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;
import com.ims.service.impl.LoanApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/loanapplications")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationServiceImpl loanApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<String> applyLoan(@RequestBody LoanApplicationRequestDTO requestDTO){
        String msg = loanApplicationService.applyLoan(requestDTO);
        return new ResponseEntity<String>(msg, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
    @GetMapping("/{lenderId}")
    public ResponseEntity<List<LoanApplicationResponseDTO>> getAllLoanApplicationForLender(@PathVariable Long lenderId){
        List<LoanApplicationResponseDTO> dtos = loanApplicationService.getAllLoanApplicationForLender(lenderId);
        return new ResponseEntity<List<LoanApplicationResponseDTO>>(dtos, HttpStatus.OK);
    }


}
