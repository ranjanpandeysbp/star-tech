package com.ims.controller;

import com.ims.dto.RequestDTO;
import com.ims.entity.startech.RequestEntity;
import com.ims.repository.CategoryRepository;
import com.ims.repository.RequestRepository;
import com.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/request")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<RequestEntity> add(@RequestBody RequestDTO requestDTO){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestor(userRepository.findById(requestDTO.getUserId()).get());
        requestEntity.setCategory(categoryRepository.findById(requestDTO.getCategoryId()).get());
        requestEntity.setRequestText(requestDTO.getName());
        requestEntity.setUpdatedAt(LocalDate.now());
        requestEntity.setCreatedAt(LocalDate.now());
        requestEntity = requestRepository.save(requestEntity);
        return new ResponseEntity<>(requestEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RequestEntity>> getAllById(@PathVariable Long userId) {
        List<RequestEntity> datas = requestRepository.findAllByRequestorId(userId);
        return new ResponseEntity<>(datas, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/requests")
    public ResponseEntity<List<RequestEntity>> getAllRequestsByCatId(@PathVariable Long categoryId) {
        List<RequestEntity> datas = requestRepository.findAllByCategoryId(categoryId);
        return new ResponseEntity<>(datas, HttpStatus.OK);
    }
}
