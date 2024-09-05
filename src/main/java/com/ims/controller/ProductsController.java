package com.ims.controller;

import com.ims.dto.ProductDTO;
import com.ims.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/products")
public class ProductsController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO input){
        input = productService.add(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> dtoList = productService.getAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
