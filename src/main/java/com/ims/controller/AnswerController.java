package com.ims.controller;


import com.ims.dto.AnswerDTO;
import com.ims.entity.startech.AnswerEntity;
import com.ims.repository.AnswerRepository;
import com.ims.repository.RequestRepository;
import com.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/answer")
public class AnswerController {


    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;

    @PostMapping
    public ResponseEntity<AnswerEntity> add(@RequestBody AnswerDTO answerDTO){
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContributor(userRepository.findById(answerDTO.getUserId()).get());
        answerEntity.setRequest(requestRepository.findById(answerDTO.getRequestId()).get());
        answerEntity.setAnswerText(answerDTO.getAnswerText());

        answerEntity.setUpdatedAt(LocalDate.now());
        answerEntity.setCreatedAt(LocalDate.now());
        answerEntity = answerRepository.save(answerEntity);
        return new ResponseEntity<>(answerEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<List<AnswerEntity>> getAllAnsByReqId(@PathVariable Long requestId) {
        List<AnswerEntity> datas = answerRepository.findAllByRequestRequestId(requestId);
        return new ResponseEntity<>(datas, HttpStatus.OK);
    }

}
