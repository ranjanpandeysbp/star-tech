package com.ims.controller;

import com.ims.dto.CategoryDTO;
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
@RequestMapping("/api/v1/auth/loanoffer")
public class LoanOfferController {
    @Autowired
    private LoanOfferServiceImpl loanOfferServiceImpl;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<LoanOfferDTO> add(@RequestBody LoanOfferDTO loanOfferDTO){
        LoanOfferDTO loanOfferResponse = loanOfferServiceImpl.add(loanOfferDTO);
        return new ResponseEntity<>(loanOfferResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<List<LoanOfferDTO>> getAll() {
        List<LoanOfferDTO> loanOfferDTOlist = loanOfferServiceImpl.getAll();
        return new ResponseEntity<>(loanOfferDTOlist, HttpStatus.OK);
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<LoanOfferDTO> update(@RequestBody LoanOfferDTO input, @RequestParam Long id) {
        LoanOfferDTO loanOfferDTO = loanOfferServiceImpl.update(input,id);
        return new ResponseEntity<>(loanOfferDTO, HttpStatus.OK);
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<LoanOfferDTO> delete(@RequestParam Long id) {
        LoanOfferDTO loanOfferDTO = loanOfferServiceImpl.delete(id);
        return new ResponseEntity<>(loanOfferDTO, HttpStatus.OK);
    }
    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<List<LoanOfferDTO>> search(@RequestBody LoanOfferDTO loanOfferDTO) {
        List<LoanOfferDTO> loanOfferDTOList = loanOfferServiceImpl.search(loanOfferDTO);
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }
    @PostMapping("/search-loan-offers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT') or hasRole('LENDER')")
    public ResponseEntity<List<LoanOfferDTO>> searchLoanOffers(@RequestParam Double loanAmount) {
        List<LoanOfferDTO> loanOfferDTOList = loanOfferServiceImpl.searchLoanOffers(loanAmount);
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }
}
