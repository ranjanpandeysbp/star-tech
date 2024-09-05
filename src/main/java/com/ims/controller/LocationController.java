package com.ims.controller;

import com.ims.dto.LocationDTO;
import com.ims.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/locations")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService;

    @PostMapping
    public ResponseEntity<LocationDTO> add(@RequestBody LocationDTO input){
        input = locationService.add(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAll() {
        List<LocationDTO> dtoList = locationService.getAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
