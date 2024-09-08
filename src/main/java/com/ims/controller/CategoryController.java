package com.ims.controller;

import com.ims.dto.CategoryDTO;
import com.ims.dto.ProductDTO;
import com.ims.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO input){
        input = categoryService.add(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> dtoList = categoryService.getAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO input, @RequestParam Long categoryId) {
        CategoryDTO categoryDTO = categoryService.update(input,categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<CategoryDTO> delete(@RequestParam Long categoryId) {
        CategoryDTO categoryDTO = categoryService.delete(categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<List<CategoryDTO>> search(@RequestBody CategoryDTO categoryDTO) {
        List<CategoryDTO> CategoryDTOList = categoryService.search(categoryDTO);
        return new ResponseEntity<>(CategoryDTOList, HttpStatus.OK);
    }
}
