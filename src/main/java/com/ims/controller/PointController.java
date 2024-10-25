package com.ims.controller;


import com.ims.entity.startech.AnswerEntity;
import com.ims.entity.startech.PointsAssignedEntity;
import com.ims.repository.PointRepository;
import com.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/point")
public class PointController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;


    @GetMapping("/{contributorId}")
    public ResponseEntity<List<PointsAssignedEntity>> getAllpointsByContId(@PathVariable Long contributorId) {
        List<PointsAssignedEntity> points = pointRepository.findPointByContributorId(contributorId);
        return new ResponseEntity<>(points, HttpStatus.OK);

    }
}
