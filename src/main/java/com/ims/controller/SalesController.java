package com.ims.controller;

import com.ims.dto.InventoryDetailRequest;
import com.ims.dto.InventoryDetailsResponse;
import com.ims.dto.SalesRequestData;
import com.ims.dto.SalesResponseData;
import com.ims.entity.SalesEntity;
import com.ims.repository.SalesRepository;
import com.ims.service.impl.SalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;

    @PostMapping("/sales")
    public ResponseEntity<SalesEntity> addSales(@RequestBody SalesEntity sales){
        SalesEntity salesEntity = salesService.addSales(sales);
        return new ResponseEntity<>(salesEntity, HttpStatus.CREATED);
    }

//    @PostMapping("/sales/{merchantId}")
//    public ResponseEntity<List<SalesEntity>> getAllSalesByMerchantId(@PathVariable Long merchantId){
//        return new ResponseEntity<>(salesRepository.findAllByMerchantId(merchantId), HttpStatus.CREATED);
//    }
    //add endpoint to give transactions between two dates
@PostMapping("/transactionDetails")
public ResponseEntity<SalesResponseData> getSalesInventoryDetail(@RequestBody SalesRequestData salesRequestData) {
    SalesResponseData salesResponseData = salesService.getSalesInventoryDetail(salesRequestData);
    return new ResponseEntity<>(salesResponseData, HttpStatus.OK);
}
}
