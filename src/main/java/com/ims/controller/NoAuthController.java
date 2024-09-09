package com.ims.controller;

import com.ims.entity.QuoteEntity;
import com.ims.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/noauth")
public class NoAuthController {
    @Autowired
    private QuoteRepository quoteRepository;

    @PostMapping("/quotes/save")
    public ResponseEntity<QuoteEntity> saveQuote(@RequestBody QuoteEntity qe){
        qe = quoteRepository.save(qe);
        return new ResponseEntity<>(qe, HttpStatus.CREATED);
    }
    @GetMapping("/quotes/details")
    public ResponseEntity<QuoteEntity> getQuote(String email){
        QuoteEntity qe = quoteRepository.findByEmail(email);
        return new ResponseEntity<>(qe, HttpStatus.CREATED);
    }
}
