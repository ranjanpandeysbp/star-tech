package com.ims.service.impl;

import com.ims.dto.ErrorDTO;
import com.ims.dto.LoanApplicationRequestDTO;
import com.ims.dto.LoanApplicationResponseDTO;
import com.ims.dto.LoanOfferDTO;
import com.ims.entity.*;
import com.ims.exception.BusinessException;
import com.ims.repository.LoanApplicationRepository;
import com.ims.repository.LoanOfferRepository;
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
        UserEntity userEntity = userRepository.findById(input.getLenderId()).get();
        loanOffers.setLender(userEntity);
        loanOffers = loanOfferRepository.save(loanOffers);
        BeanUtils.copyProperties(loanOffers, input);
        return input;
    }

    @Override
    public LoanOfferDTO update(LoanOfferDTO input, Long id) {
        return null;
    }

    @Override
    public LoanOfferDTO delete(Long id) {
        return null;
    }

    @Override
    public LoanOfferDTO get(Long id) {
        return null;
    }

    @Override
    public List<LoanOfferDTO> getAll() {
        return null;
    }

    @Override
    public List<LoanOfferDTO> search(LoanOfferDTO input) {
        return null;
    }
}
