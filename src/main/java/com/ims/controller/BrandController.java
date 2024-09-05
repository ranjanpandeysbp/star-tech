package com.ims.controller;

import com.ims.dto.BrandDTO;
import com.ims.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/brands")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;

    @PostMapping
    public ResponseEntity<BrandDTO> add(@RequestBody BrandDTO input){
        input = brandService.add(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAll() {
        List<BrandDTO> dtoList = brandService.getAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
