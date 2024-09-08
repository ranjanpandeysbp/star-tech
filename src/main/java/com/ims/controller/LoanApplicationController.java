package com.ims.controller;

import com.ims.dto.CategoryDTO;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('LENDER') or hasRole('MERCHANT')")
    @GetMapping("/{lenderId}")
    public ResponseEntity<List<LoanApplicationResponseDTO>> getAllLoanApplicationForLender(@PathVariable Long lenderId){
        List<LoanApplicationResponseDTO> dtos = loanApplicationService.getAllLoanApplicationForLender(lenderId);
        return new ResponseEntity<List<LoanApplicationResponseDTO>>(dtos, HttpStatus.OK);
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<LoanApplicationResponseDTO> update(@RequestBody LoanApplicationRequestDTO input, @RequestParam Long id) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.update(input,id);
        return new ResponseEntity<>(loanApplicationResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<LoanApplicationResponseDTO> delete(@RequestParam Long id) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.delete(id);
        return new ResponseEntity<>(loanApplicationResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<LoanApplicationResponseDTO>> search(@RequestParam Long id) {
        List<LoanApplicationResponseDTO> loanApplicationResponseDTO = loanApplicationService.search(id);
        return new ResponseEntity<>(loanApplicationResponseDTO, HttpStatus.OK);
    }
    @PatchMapping("/update-loan-amount")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<LoanApplicationResponseDTO> update(@RequestParam Double loanAmount, @RequestParam Long id) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.updateLoanAmount(loanAmount,id);
        return new ResponseEntity<>(loanApplicationResponseDTO, HttpStatus.OK);
    }
    @PatchMapping("/update-loan-status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<LoanApplicationResponseDTO> updateLoanStatus(@RequestParam String loanStatus, @RequestParam Long id) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.updateLoanStatus(loanStatus,id);
        return new ResponseEntity<>(loanApplicationResponseDTO, HttpStatus.OK);
    }

}
