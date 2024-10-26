package com.ims.controller;


import com.ims.dto.PointDTO;
import com.ims.entity.CategoryEntity;
import com.ims.entity.UserEntity;
import com.ims.entity.startech.AnswerEntity;
import com.ims.entity.startech.PointsAssignedEntity;
import com.ims.repository.CategoryRepository;
import com.ims.repository.PointRepository;
import com.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/point")
public class PointController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PointRepository pointRepository;

    @PostMapping("/save")
    public ResponseEntity<PointsAssignedEntity> addPoints(@RequestBody PointDTO pointDTO){
        PointsAssignedEntity pae = new PointsAssignedEntity();
        pae.setCreatedAt(LocalDate.now());
        CategoryEntity ce = categoryRepository.findById(pointDTO.getCategoryId()).get();
        UserEntity ue = userRepository.findById(pointDTO.getContributorId()).get();
        pae.setCategory(ce);
        pae.setContributor(ue);
        pae.setPoints(pointDTO.getPoints());

        pae = pointRepository.save(pae);
        return new ResponseEntity<>(pae, HttpStatus.CREATED);
    }

    @GetMapping("/{contributorId}")
    public ResponseEntity<PointsAssignedEntity> getAllpointsByContId(@PathVariable Long contributorId) {
        PointsAssignedEntity pa = new PointsAssignedEntity();
        List<PointsAssignedEntity> points = pointRepository.findAllByContributorId(contributorId);
        Integer updatedPoint = 0;
        for(PointsAssignedEntity p: points){
            if(p.getPoints() != null) {
                updatedPoint = p.getPoints() + updatedPoint;
                pa = p;
            }
        }
        pa.setPoints(updatedPoint);
        return new ResponseEntity<>(pa, HttpStatus.OK);
    }
}
