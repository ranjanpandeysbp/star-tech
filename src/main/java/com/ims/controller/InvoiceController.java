package com.ims.controller;

import com.ims.entity.InvoiceEntity;
import com.ims.entity.SalesEntity;
import com.ims.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceEntity> addInvoice(@RequestBody InvoiceEntity invoice){
        return new ResponseEntity<>(invoiceRepository.save(invoice), HttpStatus.CREATED);
    }

    @PostMapping("/invoices/{merchantId}")
    public ResponseEntity<List<InvoiceEntity>> getAllInvoicesByMerchantId(@PathVariable Long merchantId){
        return new ResponseEntity<>(invoiceRepository.findAllByMerchantId(merchantId), HttpStatus.CREATED);
    }
}
