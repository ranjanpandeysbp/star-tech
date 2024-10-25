package com.ims.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ims.dto.InventoryDetailRequest;
import com.ims.dto.InventoryDetailsResponse;
import com.ims.dto.LoanOfferDTO;
import com.ims.entity.QuoteEntity;
import com.ims.repository.ProductRepository;
import com.ims.repository.QuoteRepository;
import com.ims.service.impl.LoanOfferServiceImpl;
import com.ims.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/noauth")
public class NoAuthController {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private LoanOfferServiceImpl loanOfferServiceImpl;
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

    @PostMapping("/quotes/save")
    public ResponseEntity<QuoteEntity> saveQuote(@RequestBody QuoteEntity qe){
        Optional<QuoteEntity> existingCustomer = quoteRepository.findByEmail(qe.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        qe = quoteRepository.save(qe);
        return new ResponseEntity<>(qe, HttpStatus.CREATED);
    }
    @GetMapping("/quotes/details")
    public ResponseEntity<QuoteEntity> getQuote(@RequestParam String email){
        QuoteEntity qe = quoteRepository.findByEmail(email).get();
        return new ResponseEntity<>(qe, HttpStatus.OK);
    }
    @PostMapping("/search-loan-offers")
    public ResponseEntity<List<LoanOfferDTO>> searchLoanOffers(@RequestParam Double loanAmount) {
        List<LoanOfferDTO> loanOfferDTOList = loanOfferServiceImpl.searchLoanOffers(loanAmount);
        return new ResponseEntity<>(loanOfferDTOList, HttpStatus.OK);
    }
    @GetMapping("/read-json")
    public ResponseEntity<JsonNode> readJsonFile(@RequestParam String jsonFileName) {
        try {
            JsonNode jsonNode = loanOfferServiceImpl.readJsonFile(jsonFileName);
            return new ResponseEntity<>(jsonNode, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inventory-details")
    public ResponseEntity<InventoryDetailsResponse> getInventoryDetail(@RequestBody InventoryDetailRequest inventoryDetailRequest) {
        InventoryDetailsResponse inventoryDetailsResponse = productService.getInventoryDetails(inventoryDetailRequest);
        return new ResponseEntity<>(inventoryDetailsResponse, HttpStatus.OK);
    }

}
