package com.ims.controller;

import com.ims.entity.SalesEntity;
import com.ims.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class SalesController {

    @Autowired
    private SalesRepository salesRepository;

    @PostMapping("/sales")
    public ResponseEntity<SalesEntity> addSales(@RequestBody SalesEntity sales){
        //add code to deduct the quantity sold from product entity to have up to date inventory
        return new ResponseEntity<>(salesRepository.save(sales), HttpStatus.CREATED);
    }

    @PostMapping("/sales/{merchantId}")
    public ResponseEntity<List<SalesEntity>> getAllSalesByMerchantId(@PathVariable Long merchantId){
        return new ResponseEntity<>(salesRepository.findAllByMerchantId(merchantId), HttpStatus.CREATED);
    }
    //add endpoint to give transactions between two dates
}
