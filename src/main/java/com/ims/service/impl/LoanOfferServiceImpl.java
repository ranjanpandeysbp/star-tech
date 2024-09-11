package com.ims.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dto.ErrorDTO;
import com.ims.dto.LoanOfferDTO;
import com.ims.dto.UserDTO;
import com.ims.entity.*;
import com.ims.exception.BusinessException;
import com.ims.repository.LoanOfferRepository;
import com.ims.repository.UserRepository;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanOfferServiceImpl implements ImsService<LoanOfferDTO,LoanOfferDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanOfferRepository loanOfferRepository;

    @Override
    public LoanOfferDTO add(LoanOfferDTO input) {
        LoanOffers loanOffers = new LoanOffers();
        BeanUtils.copyProperties(input, loanOffers);
        loanOffers.setLoanCriteria(input.getLoanCriteria());
        loanOffers.setMaxLoanAmount(input.getMaxLoanAmount());
        loanOffers.setMinLoanAmount(input.getMinLoanAmount());
        loanOffers.setMaxInterestRate(input.getMaxInterestRate());
        loanOffers.setMinInterestRate(input.getMinInterestRate());
        loanOffers.setIndustry(input.getIndustry());
        UserEntity userEntity = userRepository.findById(input.getLenderId()).get();
        loanOffers.setLender(userEntity);
        loanOffers = loanOfferRepository.save(loanOffers);
        BeanUtils.copyProperties(loanOffers, input);
        return input;
    }

    @Override
    public LoanOfferDTO update(LoanOfferDTO input, Long id) {
        LoanOffers loanOffers = loanOfferRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan offer with Id: "+id))));
        BeanUtils.copyProperties(input, loanOffers);
        loanOffers.setId(id);
        loanOffers = loanOfferRepository.save(loanOffers);
        BeanUtils.copyProperties(loanOffers, input);
        return input;
    }

    @Override
    public LoanOfferDTO delete(Long id) {
        LoanOffers loanOffers = loanOfferRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan offer  with Id: "+id))));
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        BeanUtils.copyProperties(loanOffers, loanOfferDTO);
        loanOfferRepository.deleteById(id);
        return loanOfferDTO;
    }

    @Override
    public LoanOfferDTO get(Long id) {
        LoanOffers loanOffers = loanOfferRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan offer with Id: "+id))));
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        BeanUtils.copyProperties(loanOffers, loanOfferDTO);
        loanOfferDTO.setId(loanOffers.getId());
        return loanOfferDTO;
    }

    @Override
    public List<LoanOfferDTO> getAll() {
        List<LoanOffers> entityList = loanOfferRepository.findAll();
        return entityList.stream().map(loanOfferEntity -> {
            LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
            BeanUtils.copyProperties(loanOfferEntity, loanOfferDTO);
            loanOfferDTO.setLoanCriteria(loanOfferEntity.getLoanCriteria());
            loanOfferDTO.setMaxLoanAmount(loanOfferEntity.getMaxLoanAmount());
            loanOfferDTO.setMinLoanAmount(loanOfferEntity.getMinLoanAmount());
            loanOfferDTO.setMaxInterestRate(loanOfferEntity.getMaxInterestRate());
            loanOfferDTO.setMinInterestRate(loanOfferEntity.getMinInterestRate());
            loanOfferDTO.setId(loanOfferEntity.getId());
            loanOfferDTO.setLenderId(loanOfferEntity.getLender().getId());
            return loanOfferDTO;
        }).collect(Collectors.toList());
    }
      public List<LoanOfferDTO> getAllByLender(Long lenderId) {
        List<LoanOffers> entityList = loanOfferRepository.findAllByLenderId(lenderId);
        return entityList.stream().map(loanOfferEntity -> {
            LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
            BeanUtils.copyProperties(loanOfferEntity, loanOfferDTO);
            loanOfferDTO.setLoanCriteria(loanOfferEntity.getLoanCriteria());
            loanOfferDTO.setMaxLoanAmount(loanOfferEntity.getMaxLoanAmount());
            loanOfferDTO.setMinLoanAmount(loanOfferEntity.getMinLoanAmount());
            loanOfferDTO.setMaxInterestRate(loanOfferEntity.getMaxInterestRate());
            loanOfferDTO.setMinInterestRate(loanOfferEntity.getMinInterestRate());
            loanOfferDTO.setId(loanOfferEntity.getId());
            loanOfferDTO.setLenderId(loanOfferEntity.getLender().getId());
            return loanOfferDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LoanOfferDTO> search(LoanOfferDTO input) {
        return null;
    }
    public List<LoanOfferDTO> searchLoanOffers(Double loanAmount) {
        List<LoanOffers> loanOfferList = loanOfferRepository.findByMinLoanAmountLessThanAndMaxLoanAmountGreaterThan(loanAmount,loanAmount);
        LoanOfferDTO loanOfferDTO =  null;
        List<LoanOfferDTO> dtos = new ArrayList<>();
        for(LoanOffers loanOffer: loanOfferList){
            loanOfferDTO = new LoanOfferDTO();
            BeanUtils.copyProperties(loanOffer, loanOfferDTO);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(loanOffer.getLender(), userDTO);
            loanOfferDTO.setLender(userDTO);
            loanOfferDTO.setId(loanOffer.getId());
            dtos.add(loanOfferDTO);
        }
        return dtos;
    }
    public JsonNode readJsonFile() throws IOException {
        Path jsonFilePath = new ClassPathResource("json/tax-profit-loss.json").getFile().toPath();
        String jsonContent = Files.readString(jsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonContent);
    }
}
