package com.ims.controller;

import com.ims.dto.BulkUploadDTO;
import com.ims.dto.ProductDTO;
import com.ims.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/products")
public class ProductsController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO input){
        input = productService.add(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @GetMapping("/{merchantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<ProductDTO>> getAllByUser(@PathVariable Long merchantId) {
        List<ProductDTO> dtoList = productService.getAllByUser(merchantId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO input, @RequestParam Long productId) {
        ProductDTO productDTO = productService.update(input,productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<ProductDTO> delete(@RequestParam Long productId) {
        ProductDTO productDTO = productService.delete(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<ProductDTO>> search(@RequestBody ProductDTO productDTO) {
        List<ProductDTO> productDTOList = productService.search(productDTO);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @PostMapping("/bulk-upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<String> bulkUpload(@RequestBody BulkUploadDTO inventoryDetails) {
        String uploadMessage = productService.bulkUpload(inventoryDetails);
        return new ResponseEntity<>(uploadMessage, HttpStatus.OK);
    }

    @GetMapping("/unique-products/{merchantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<ProductDTO>> bulkUpload(@PathVariable Long merchantId) {
        List<ProductDTO> productDetails = productService.getProductDetails(merchantId);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }
}
