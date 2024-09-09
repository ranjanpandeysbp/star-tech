package com.ims.controller;

import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;
import com.ims.dto.LoanOfferDTO;
import com.ims.service.impl.LoanApplicationServiceImpl;
import com.ims.service.impl.LoanOfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/loanaoffer")
public class LoanOfferController {
    @Autowired
    private LoanOfferServiceImpl loanOfferServiceImpl;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')or hasRole('LENDER')")
    public ResponseEntity<LoanOfferDTO> add(@RequestBody LoanOfferDTO loanOfferDTO){
        LoanOfferDTO loanOfferResponse = loanOfferServiceImpl.add(loanOfferDTO);
        return new ResponseEntity<>(loanOfferResponse, HttpStatus.CREATED);
    }
}
