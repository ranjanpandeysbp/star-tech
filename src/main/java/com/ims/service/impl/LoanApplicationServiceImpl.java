package com.ims.service.impl;

import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;
import com.ims.entity.ECurrency;
import com.ims.entity.EStatus;
import com.ims.entity.LoanApplication;
import com.ims.entity.UserEntity;
import com.ims.repository.LoanApplicationRepository;
import com.ims.repository.UserRepository;
import com.ims.service.LoanApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private UserRepository userRepository;

    public String applyLoan(LoanApplicationRequestDTO requestDTO){

        LoanApplication loanApplication = new LoanApplication();
        BeanUtils.copyProperties(requestDTO, loanApplication);
        UserEntity lender = userRepository.findById(requestDTO.getLenderId()).get();
        Random random = new Random();
        int randomNum = random.nextInt((10 - 1) + 1) + 1;
        loanApplication.setLender(lender);
        loanApplication.setRiskScore(Double.valueOf(randomNum));
        loanApplication.setELoanStatus(EStatus.INITIATED);
        UserEntity merchant = userRepository.findById(requestDTO.getMerchantId()).get();
        loanApplication.setMerchant(merchant);
        loanApplication.setCurrency(ECurrency.valueOf(requestDTO.getCurrency()));
        loanApplication = loanApplicationRepository.save(loanApplication);

        return "Loan applied successfully, reference Id is: "+loanApplication.getId();
    }

    public LoanApplicationResponseDTO getLoanDetails(Long loanApplicationId){
       LoanApplication le = loanApplicationRepository.findById(loanApplicationId).get();
       LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
       BeanUtils.copyProperties(le, responseDTO);
       return responseDTO;
    }

    public List<LoanApplicationResponseDTO> getAllLoanApplicationForLender(Long lenderId){
        List<LoanApplication> loanApplicationList = loanApplicationRepository.findAllByLenderId(lenderId);
        List<LoanApplicationResponseDTO> dtos = new ArrayList<>();
        LoanApplicationResponseDTO responseDTO = null;
        for(LoanApplication la: loanApplicationList){
            responseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(la, responseDTO);
            dtos.add(responseDTO);
        }
        return dtos;
    }
}
