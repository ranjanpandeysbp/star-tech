package com.ims.service.impl;

import com.ims.dto.CategoryDTO;
import com.ims.dto.ErrorDTO;
import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;
import com.ims.entity.*;
import com.ims.exception.BusinessException;
import com.ims.repository.LoanApplicationRepository;
import com.ims.repository.UserRepository;
import com.ims.service.ImsService;
import com.ims.service.LoanApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService{

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
        loanApplication.setELoanStatusLender(EStatus.INITIATED);
        loanApplication.setELoanStatusMerchant(EStatus.INITIATED);
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
    public LoanApplicationResponseDTO update(LoanApplicationRequestDTO input, Long id) {
        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+id))));
        BeanUtils.copyProperties(input, pe);
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(id);
        pe.setLoanAmountRequested(input.getLoanAmountRequested());
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(pe, responseDTO);
        return responseDTO;
    }

    public LoanApplicationResponseDTO delete(Long id) {

        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan with Id: "+id))));
        LoanApplicationResponseDTO loanApplicationResponseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, loanApplicationResponseDTO);
        loanApplicationRepository.deleteById(id);
        return loanApplicationResponseDTO;
    }
    public List<LoanApplicationResponseDTO> search(Long id) {
        List<LoanApplication> loanApplications = loanApplicationRepository.findAllByLenderId(id);
        LoanApplicationResponseDTO loanApplicationResponseDTO =  null;
        List<LoanApplicationResponseDTO> dtos = new ArrayList<>();
        for(LoanApplication loanApplication : loanApplications){
            loanApplicationResponseDTO = new LoanApplicationResponseDTO();
            BeanUtils.copyProperties(loanApplication, loanApplicationResponseDTO);;
            loanApplicationResponseDTO.setId(loanApplication.getId());
            dtos.add(loanApplicationResponseDTO);
        }
        return dtos;
    }
    public LoanApplicationResponseDTO updateLoanAmount(Double loanAmount, Long id) {
        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+id))));
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(id);
        pe.setLoanAmountRequested(loanAmount);
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, responseDTO);
        return responseDTO;
    }

    public LoanApplicationResponseDTO updateLoanStatus(String loanStatus, Long id, String role) {
        LoanApplication pe = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Loan application with Id: "+id))));
        pe.setUpdatedDateTime(LocalDateTime.now());
        pe.setId(id);
        if(role.equalsIgnoreCase("ROLE_MERCHANT")){
            pe.setELoanStatusMerchant(EStatus.valueOf(loanStatus));
        }else{
            pe.setELoanStatusLender(EStatus.valueOf(loanStatus));
        }
        pe = loanApplicationRepository.save(pe);
        LoanApplicationResponseDTO responseDTO = new LoanApplicationResponseDTO();
        BeanUtils.copyProperties(pe, responseDTO);
        if(role.equalsIgnoreCase("ROLE_MERCHANT")){
            responseDTO.setELoanStatusMerchant(loanStatus);
        }else{
            responseDTO.setELoanStatusLender(loanStatus);
        }
        return responseDTO;
    }
}
