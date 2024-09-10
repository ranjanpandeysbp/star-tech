package com.ims.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ims.dto.LoanOfferDTO;
import com.ims.entity.QuoteEntity;
import com.ims.repository.QuoteRepository;
import com.ims.service.impl.LoanOfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/noauth")
public class NoAuthController {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private LoanOfferServiceImpl loanOfferServiceImpl;

    @PostMapping("/quotes/save")
    public ResponseEntity<QuoteEntity> saveQuote(@RequestBody QuoteEntity qe){
        qe = quoteRepository.save(qe);
        return new ResponseEntity<>(qe, HttpStatus.CREATED);
    }
    @GetMapping("/quotes/details")
    public ResponseEntity<QuoteEntity> getQuote(@RequestParam String email){
        QuoteEntity qe = quoteRepository.findByEmail(email);
        return new ResponseEntity<>(qe, HttpStatus.CREATED);
    }
    @PostMapping("/search-loan-offers")
    public ResponseEntity<List<LoanOfferDTO>> searchLoanOffers(@RequestParam Double loanAmount) {
        List<LoanOfferDTO> loanOfferDTOList = loanOfferServiceImpl.searchLoanOffers(loanAmount);
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }
    @GetMapping("/read-json")
    public ResponseEntity<JsonNode> readJsonFile() {
        try {
            JsonNode jsonNode = loanOfferServiceImpl.readJsonFile();
            return new ResponseEntity<>(jsonNode, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
